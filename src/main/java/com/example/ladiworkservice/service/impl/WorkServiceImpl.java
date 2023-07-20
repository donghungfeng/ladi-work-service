package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.JwtTokenProvider;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.CheckInResponse;
import com.example.ladiworkservice.controller.request.CheckOutRequest;
import com.example.ladiworkservice.controller.request.CheckWorkActiveRequest;
import com.example.ladiworkservice.controller.request.CreateWorkRequest;
import com.example.ladiworkservice.dto.AccountDto;
import com.example.ladiworkservice.dto.StatisticPerformanceSaleDto;
import com.example.ladiworkservice.dto.WorkDto;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.WorkService;
import com.example.ladiworkservice.model.*;
import com.example.ladiworkservice.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl extends BaseServiceImpl<Work> implements WorkService {
    @Autowired
    WorkRepository workRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CallInfoRepository callInfoRepository;

    @Autowired
    CustomWorkRepository customWorkRepository;

    @Autowired
    CustomDataRepository customDataRepository;

    @Autowired
    DataRepository dataRepository;
    @Autowired
    private SaleKpiStatisticRepository saleKpiStatisticRepository;
    @Autowired
    private SaleKpiStatisticImpl saleKpiStatisticImpl;
    @Override
    protected BaseRepository<Work> getRepository() {
        return workRepository;
    }

    @Override
    public BaseResponse createWork(CreateWorkRequest createWorkRequest) {
        Account account = accountRepository.findAllById(createWorkRequest.getNhanVienId());
        if (account == null){
            return new BaseResponse(500, "Account not found", "Create Fail");
        }
        if(workRepository.findAllByIsActiveAndAccount(0, account) != null) {
            return new BaseResponse(500, "Tài khoản hiện đã đăng nhập ở thiết bị khác", "Create Fail");
        }
        Work work = modelMapper.map(createWorkRequest, Work.class);
        work.setAccount(account);
        CallInfo callInfo = callInfoRepository.findAllById(createWorkRequest.getLine());
        if (callInfo != null){
            if (callInfo.getAccount() != null || callInfo.getIsActive() != 1){
                return new BaseResponse(500, "Line này đã được sử dụng xin hãy chọn line khác!", "FAIL");
            }
            callInfo.setAccount(account);
            callInfo.setIsActive(0);
            callInfo = callInfoRepository.save(callInfo);
        }
        //tạo mới thông tin thống kê hieu suất sale khi checkin
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Long date = Long.parseLong(today.format(formatter));
        SalerKpiStatistics statistics=saleKpiStatisticRepository.findAllByDateAndSaler(date,account);
        String shopcode=createWorkRequest.getShopCode();
        Long shopID=shopRepository.findShopByCode(createWorkRequest.getShopCode()).getId();
        if(statistics==null){
            statistics=new SalerKpiStatistics();
            statistics.setSaler(account);
            statistics.setDate(date);
            statistics.setShop(shopRepository.findShopByCode(createWorkRequest.getShopCode()));
            saleKpiStatisticRepository.save(statistics);
        }


        work = workRepository.save(work);
        CheckInResponse checkInResponse = new CheckInResponse(work, callInfo);
        return new BaseResponse(200, "OK", checkInResponse);
    }

    @Override
    public BaseResponse getAllWork(String jwt, String startDate, String endDate) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<WorkDto> workDtoList = new ArrayList<>();
        if (account.getRole().equals("admin")){
            List<Work> workList = customWorkRepository.finWorkByConditions(startDate, endDate, null, null, -9999);
            for (int i = 0; i<workList.size(); i++){
                AccountDto accountDto = modelMapper.map(workList.get(i).getAccount(), AccountDto.class);
                WorkDto workDto = modelMapper.map(workList.get(i),WorkDto.class);
                workDto.setAccount(accountDto);
                workDtoList.add(workDto);
            }
        }
        else{
            List<Work> workList = customWorkRepository.finWorkByConditions(startDate, endDate, account, null, -9999);
            for (int i = 0 ; i<workList.size(); i++){
                AccountDto accountDto = modelMapper.map(workList.get(i).getAccount(),AccountDto.class);
                WorkDto workDto = modelMapper.map(workList.get(i),WorkDto.class);
                workDto.setAccount(accountDto);
                workDtoList.add(workDto);
            }
        }
        return new BaseResponse(200, "OK", workDtoList);

    }

    @Override
    public BaseResponse checkOut(CheckOutRequest checkOutRequest, String shopCode) {
        Work work = workRepository.findAllById(checkOutRequest.getId());
        if (work == null){
            return new BaseResponse(500, "Work not found", "Checkout Fail");
        }
        //cập nhật thống kê hiệu suất sale khi sale checkout
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long date = Long.parseLong(formatter.format(today));
        SalerKpiStatistics statistics=saleKpiStatisticRepository.findAllByDateAndSaler(date,work.getAccount());
        if(statistics==null){
            statistics=new SalerKpiStatistics();
            statistics.setSaler(work.getAccount());
            statistics.setDate(date);
            saleKpiStatisticRepository.save(statistics);
        }
        saleKpiStatisticImpl.updateSalerKpiStatistics(date,work.getShopCode(),work.getAccount().getId());

        List<Data> dataList = customDataRepository.checkOut("0,1,2,3,4,5,6,7,8,9,10,11", "0", String.valueOf(checkOutRequest.getTimeOut()), work.getAccount(), shopCode);
        for (int i = 0; i<dataList.size(); i++){
            if (dataList.get(i).getStatus() == 1 || dataList.get(i).getStatus() == 2){
                dataList.get(i).setStatus(0);
            }
            dataList.get(i).setAccount(null);
        }
        work.setTimeOut(checkOutRequest.getTimeOut());
        work.setTotalOrder(checkOutRequest.getTotalOrder());
        work.setSuccessOrder(checkOutRequest.getSuccessOrder());
        work.setProcessedOrder(checkOutRequest.getProcessedOrder());
        work.setOnlyOrder(checkOutRequest.getOnlyOrder());
        work.setProcessedOnlyOrder(checkOutRequest.getProcessedOnlyOrder());
        work.setIsActive(-1);
        CallInfo callInfo = callInfoRepository.findAllByAccount(work.getAccount());
        if (callInfo != null){
            callInfo.setAccount(null);
            callInfo.setIsActive(1);
            callInfoRepository.save(callInfo);
        }

        workRepository.save(work);
        return new BaseResponse(200, "OK", "Checkout Success");
    }

    @Override
    public BaseResponse getAllActive(String shopCode) {
        List<Work> workList = customWorkRepository.finWorkByConditions(null, null, null, shopCode, 0);
        List<WorkDto> workDtoList = new ArrayList<>();
        for (int i = 0 ; i<workList.size(); i++){
            AccountDto accountDto = new AccountDto(workList.get(i).getAccount().getId(), workList.get(i).getAccount().getUserName(), workList.get(i).getAccount().getUserName(), workList.get(i).getAccount().getShop(), workList.get(i).getAccount().getRole());
            WorkDto workDto = modelMapper.map(workList.get(i), WorkDto.class);
            workDto.setAccount(accountDto);
            workDtoList.add(workDto);
        }
        return new BaseResponse(200, "OK", workDtoList);
    }

    @Override
    public BaseResponse checkWorkActive(CheckWorkActiveRequest checkWorkActive, String shopCode) {
        Account account = accountRepository.findAllById(checkWorkActive.getNhanVienId());
        if (account == null){
            return new BaseResponse(500, "Account Not Found!", null);
        }
        Work work = workRepository.findAllByIsActiveAndAccount(0, account);
        if (work == null){
            return new BaseResponse(500, "NOT ACCOUNT CHECKING!", null);
        }
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long date = Long.parseLong(formatter.format(nowDate));
        Long startDate = work.getTimeIn();
        Long endDate = date;
        shopCode = work.getShopCode();
        int totalOrder = customDataRepository.checkOut("1,2,3,4,5,6,7,8,9", String.valueOf(startDate), String.valueOf(endDate), account, shopCode).size();
        int successOrder = customDataRepository.checkOut("7,8", String.valueOf(startDate), String.valueOf(endDate), account, shopCode).size();
        int processedOrder = customDataRepository.checkOut("2,3,4,5,6,7,8,9", String.valueOf(startDate), String.valueOf(endDate), account, shopCode).size();
        int onlyOrder = customDataRepository.checkOut("1,2,3,4,5,6,7,8", String.valueOf(startDate), String.valueOf(endDate), account, shopCode).size();
        int processedOnlyOrder = customDataRepository.checkOut("2,3,4,5,6,7,8", String.valueOf(startDate), String.valueOf(endDate), account, shopCode).size();
        work.setTotalOrder(totalOrder);
        work.setSuccessOrder(successOrder);
        work.setProcessedOrder(processedOrder);
        work.setOnlyOrder(onlyOrder);
        work.setProcessedOnlyOrder(processedOnlyOrder);
        work.setTimeOut(endDate);
        AccountDto accountDto = modelMapper.map(work.getAccount(), AccountDto.class);
        WorkDto workDto = modelMapper.map(work, WorkDto.class);
        CallInfo callInfo = callInfoRepository.findAllByAccount(work.getAccount());
        if (callInfo != null){
            workDto.setCallInfo(callInfo);
        }
        workDto.setAccount(accountDto);
        return new BaseResponse(200, "OK", workDto);
    }

    @Override
    public BaseResponse infoCheckout(Long id) {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long date = Long.parseLong(formatter.format(nowDate));
        Work work = workRepository.findById(id).get();
        Long startDate = work.getTimeIn();
        Long endDate = date;
        work.setTimeOut(endDate);
        AccountDto accountDto = modelMapper.map(work.getAccount(), AccountDto.class);
        WorkDto workDto = modelMapper.map(work, WorkDto.class);
        workDto.setAccount(accountDto);
        return new BaseResponse(200, "OK", workDto);
    }

    @Override
    public BaseResponse statisticPerformanceSale(String startDate, String endDate, String shopCode) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        List<StatisticPerformanceSaleDto> statisticPerformanceSaleDto = workRepository.statisticPerformanceSale(Long.parseLong(startDate), Long.parseLong(endDate), shopCode);
        List<StatisticPerformanceSaleDto> resultList = statisticPerformanceSaleDto.stream().map((item) -> {
            if (item.getTotalOrder() == 0){
                item.setPercentProcessedOrder(df.format(0));
                item.setPercentSuccessOrder(df.format(0));
                item.setPercentOnlyOrder(df.format(0));
                item.setPercentProcessedOnlyOrder(df.format(0));
            }else {
                if (item.getSuccessOrder() == 0){
                    item.setPercentSuccessOrder(df.format(0));
                }else {
                    item.setPercentSuccessOrder(df.format((item.getSuccessOrder()/(item.getTotalOrder()*1.0))*100));
                }
                if (item.getProcessedOrder() == 0){
                    item.setPercentProcessedOrder(df.format(0));
                }else {
                    item.setPercentProcessedOrder(df.format((item.getSuccessOrder()/(item.getProcessedOrder()*1.0))*100));
                }
                if (item.getOnlyOrder() == 0){
                    item.setPercentOnlyOrder(df.format(0));
                }else {
                    item.setPercentOnlyOrder(df.format((item.getSuccessOrder()/(item.getOnlyOrder()*1.0))*100));
                }
                if (item.getProcessedOnlyOrder() == 0){
                    item.setPercentProcessedOnlyOrder(df.format(0));
                }else {
                    item.setPercentProcessedOnlyOrder(df.format((item.getSuccessOrder()/(item.getProcessedOnlyOrder()*1.0))*100));
                }
            }
            return item;
        }).collect(Collectors.toList());

        return new BaseResponse(200, "OK", resultList);
    }

    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
