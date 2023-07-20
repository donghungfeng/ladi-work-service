package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.JwtTokenProvider;
import com.example.ladiworkservice.configurations.global_variable.BoLStatus;
import com.example.ladiworkservice.configurations.global_variable.DATA;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.ProductShippingOrder;
import com.example.ladiworkservice.controller.reponse.TrackingGHSVResponse;
import com.example.ladiworkservice.controller.reponse.TrackingGHSVResult;
import com.example.ladiworkservice.controller.request.AssignJobRequest;
import com.example.ladiworkservice.controller.request.CreateBoLRequest;
import com.example.ladiworkservice.controller.request.DataRequest;
import com.example.ladiworkservice.controller.request.MarketingPerformanceReq;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.query.CustomRsqlVisitor;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.*;
import com.example.ladiworkservice.service.*;
import com.example.ladiworkservice.shippingorders.ghn.request.TrackingBodyGHSVRequest;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.UpdateStatusDataWebhookGHSVRP;
import com.example.ladiworkservice.shippingorders.ghsv.request.UpdateStatusDataWebhookGHSVRQ;
import com.example.ladiworkservice.util.DateUtil;
import com.example.ladiworkservice.model.*;
import com.example.ladiworkservice.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {

    private static final List<Integer> orderStatus = Arrays.asList(7, 8, 10, 11);
    private static final String ROLE_MKT = "marketing";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    DataRepository dataRepository;

    @Autowired
    StatisticsGeneralRepository statisticsGeneralRepository;

    @Autowired
    StatisticsGeneralService statisticsGeneralService;

    @Autowired
    BoLRepository boLRepository;

    @Autowired
    BoLDetailRepository boLDetailRepository;

    @Autowired
    BoLService boLService;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    OrderReponsitory orderReponsitory;

    @Autowired
    ConvertStatusShippingToData convertStatusShippingToData;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UtmMediumRepository utmMediumRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomDataRepository customDataRepository;

    @Autowired
    SaleKpiStatisticRepository saleKpiStatisticRepository;

    @Autowired
    CustomWorkRepository customWorkRepository;

    @Autowired
    ConfigRepository configRepository;

    @Autowired
    SubProductRepository subProductRepository;

    @Autowired
    OrderGHSVRepository orderGHSVRepository;

    @Autowired
    private CostService costService;
    @Autowired
    private AcountService acountService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    protected BaseRepository<Data> getRepository() {
        return dataRepository;
    }

    @Override
    public BaseResponse getAllData(String jwt, String status, String startDate, String endDate, String shopCode, int page, int size) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<DataDto> dataDtoList = new ArrayList<>();
        if (account.getRole().equals("admin")) {
            List<Data> dataList = customDataRepository.finDataByConditions(status, startDate, endDate, null, shopCode, page, size);
//            for (int i = 0; i < dataList.size(); i++) {
//                if (dataList.get(i).getAccount() == null){
//                    DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
//                    dataDtoList.add(dataDto);
//                }
//                else {
//                    AccountDto accountDto = new AccountDto(dataList.get(i).getAccount().getId(), dataList.get(i).getAccount().getUserName(), dataList.get(i).getAccount().getFullName(), dataList.get(i).getAccount().getShop(), dataList.get(i).getAccount().getRole());
//                    DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
//                    dataDto.setAccount(accountDto);
//                    dataDtoList.add(dataDto);
//                }
//            }
            return new BaseResponse(200, "OK", dataList);
        } else {
            List<Data> dataList = customDataRepository.finDataByConditions(status, startDate, endDate, account, shopCode, page, size);
//            for (int i = 0; i<dataList.size(); i++){
//                AccountDto accountDto = modelMapper.map(dataList.get(i).getAccount(), AccountDto.class);
//                DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
//                dataDto.setAccount(accountDto);
//                dataDtoList.add(dataDto);
//            }
            return new BaseResponse(200, "OK", dataList);
        }

    }

    @Override
    public BaseResponse createData(Data data, String shopCode) {

        DataDto dataDto = this.create(data, shopCode);

        return new BaseResponse(200, "OK", dataDto);
    }

    private DataDto create(Data data, String shopCode) {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dateOnlyFormatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        dateOnlyFormatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long date = Long.parseLong(formatter.format(nowDate));
        Long dateOnly = Long.parseLong(dateOnlyFormatter.format(nowDate));
        data.setDate(date);
        data.setDateOnly(dateOnly);
        data.setDateChangedOnly(dateOnly);
        data.setShopCode(shopCode);
        try {
            data.setIpAddress(getClientIp());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (data.getUtm_medium() == null || data.getUtm_medium().equals("")) {
            data.setUtm_medium("UNDEFINE");
        }
        List<Work> workList = customWorkRepository.finWorkByConditions(null, null, null, shopCode, 0);
        DataDto dataDto = new DataDto();
        if (!workList.isEmpty()) {
            Random rand = new Random();
            int ranNum = rand.nextInt(workList.size());
            data.setAccount(workList.get(ranNum).getAccount());
            data.setStatus(1);
            data.setDateChanged(date);
            AccountDto accountDto = modelMapper.map(workList.get(ranNum).getAccount(), AccountDto.class);
            dataDto = modelMapper.map(data, DataDto.class);
            dataDto.setAccount(accountDto);
        }
        dataRepository.save(data);

        return dataDto;
    }


    @Override
    public DataDto createV2(Object data, String shopCode) throws JsonProcessingException {
        Data entity = modelMapper.map(data, Data.class);
        entity.setDataInfo(objectMapper.writeValueAsString(data));
        return this.create(entity, shopCode);
    }


    @Override
    public  BaseResponse assignWork(String jwt, AssignJobRequest assignJobRequest) throws JsonProcessingException, NoSuchAlgorithmException {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account accountLogin = accountRepository.findByUserName(userName);
        ArrayList<Data> datas = new ArrayList<>();
        for (DataRequest data : assignJobRequest.getDataList()) {
            Account account = accountRepository.findById(accountLogin.getId()).orElse(null);
            Data dataResult = modelMapper.map(data, Data.class);
            Data checkData = dataRepository.findAllById(data.getId());

            if (dataResult.getStatus() == DATA.STATUS_SUCCESS && dataResult.getStatus() != checkData.getStatus()){
                if (statisticsGeneralService.checkStatisticsGeneral(dataResult).isEmpty()){
                    createStatisticGeneral(dataResult);
                }else {
                    updateStatisticGeneral(dataResult);
                }
            }

            if (dataResult.getStatus() == DATA.STATUS_SUCCESS && dataResult.getBoL() == null){
                BaseResponse response =  createExportBoL(account, dataResult);
                if (response.CODE == 500){
                    return response;
                }

            }else if (dataResult.getStatus() == DATA.STATUS_SUCCESS && dataResult.getBoL() != null){
                cancelExportBoL(dataResult);
                BaseResponse response =  createExportBoL(account, dataResult);
                if (response.CODE == 500){
                    return response;
                }
            }

            //Cong lai so luong co the ban cua mau ma khi huy tu thanh cong sang that bai
            if (checkData.getStatus() == DATA.STATUS_SUCCESS && dataResult.getStatus() == DATA.CANCEL_ORDER){
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<ProductShippingOrder> productShippingOrderList = objectMapper.readValue(data.getProductIds(), new TypeReference<List<ProductShippingOrder>>() {});
                List<SubProduct> subProductList = new ArrayList<>();
                for (ProductShippingOrder item : productShippingOrderList){
                    SubProduct subProduct = subProductRepository.findAllById(item.getProduct().getId());
                    subProduct.setAvailableQuantity(subProduct.getAvailableQuantity()+item.getQuantity());
                    subProductList.add(subProduct);
                }
                if (dataResult.getSaleAccount() != null){
                    Long date = Long.parseLong(String.valueOf(checkData.getTimeOrderSuccess()).substring(0,7));
                    SalerKpiStatistics statistics=saleKpiStatisticRepository.findAllByDateAndSaler(date,checkData.getSaleAccount());
                    if (statistics != null){
                        statistics.setTotalOrderSuccess(statistics.getTotalOrderSuccess() - 1L);
                        saleKpiStatisticRepository.save(statistics);
                    }
                }
                if (!statisticsGeneralService.checkStatisticsGeneral(dataResult).isEmpty()){
                    updateStatisticGeneralWhenCancelData(dataResult);
                }
                cancelExportBoL(checkData);
                subProductRepository.saveAll(subProductList);
            }

            // lưu time_order_success, saleid khi sale chốt đơn thành công----Editors by dat
            if (dataResult.getStatus() == 7 ) {
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                Long time_order_success = Long.parseLong(formatter.format(today));
                dataResult.setTimeOrderSuccess(time_order_success);
                dataResult.setSaleAccount(account);
            }

            // lưu saleid khi sale chốt đơn thất bại hoặc trùng ----Editors by dat
            if ((dataResult.getStatus() == 6 || dataResult.getStatus() == 9) ) {
                dataResult.setSaleAccount(account);
            }

            Data d = dataRepository.findAllById(data.getId());
            if (d != null) {
                int status = dataResult.getStatus();
                    if(d.getStatus() == 7 && d.getTimeOrderSuccess()!=null) {
                        long timeOrderSuccess = (d.getTimeOrderSuccess() / 1000000L);
                        SalerKpiStatistics salerKpiStatistics = saleKpiStatisticRepository.findSalerKpiStatisticsBySaleIdAndDateAndShop(d.getSaleAccount().getId(), timeOrderSuccess);
                        if (accountLogin.getRole().equals("admin") && salerKpiStatistics != null && (status >= 7 && status <= 12)) {
                            double priceDifference = dataResult.getPrice() - d.getPrice();
                            if (priceDifference != 0) {
                                salerKpiStatistics.setSales(salerKpiStatistics.getSales() + priceDifference);
                                if (status != 10) {
                                    salerKpiStatistics.setRevenue(salerKpiStatistics.getRevenue() + priceDifference);
                                } else {
                                    salerKpiStatistics.setRevenue((double) 0);
                                }
                                if (status == 12) {
                                    salerKpiStatistics.setActualRevenue(salerKpiStatistics.getActualRevenue() + priceDifference);
                                }
                                long totalOrderSuccess = salerKpiStatistics.getTotalOrderSuccess();
                                salerKpiStatistics.setAverageOrderValue(salerKpiStatistics.getSales() / totalOrderSuccess);
                                SalerKpiStatistics statistics = modelMapper.map(salerKpiStatistics, SalerKpiStatistics.class);
                                saleKpiStatisticRepository.save(statistics);
                            }
                        }
                    }
            }

            dataResult.setAccount(accountRepository.findAllById(data.getNhanVienId()));
            datas.add(dataResult);
        }

        dataRepository.saveAll(datas);
        return new BaseResponse(200, "Success!", null);
    }


    @Override
    public BaseResponse getAllDataAccountNull(String status, String shopCode) {
        List<Data> dataList = customDataRepository.findDataByAccountNull(status, shopCode);
        return new BaseResponse(200, "OK", dataList);
    }

    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public BaseResponse statisticByUtmMedium() {
        List<UtmMedium> utmMediumList = utmMediumRepository.findAll();
        List<String> codeList = new ArrayList<>();
        codeList.add("ALL");
        List<List<Object>> dataList = new ArrayList<>();
        dataList.add(dataRepository.statisticUtmMedium());
        Map<String, List> map = new HashMap<String, List>();
        for (int i = 0; i < utmMediumList.size(); i++) {
            codeList.add(utmMediumList.get(i).getCode());
            dataList.add(dataRepository.statisticUtmMediumByMedium(utmMediumList.get(i).getCode()));
        }
        map.put("code", codeList);
        map.put("data", dataList);
        return new BaseResponse(200, "OK", map);
    }

    @Override
    public BaseResponse statisticcalrevenueByDay() {
        List<StatisticalRevenueByDayDto> dataList = dataRepository.statisticcalrevenueByDay();
        List<StatisticalRevenueByDayResult> resultList = new ArrayList<>();
        for (StatisticalRevenueByDayDto item : dataList) {
            StatisticalRevenueByDayResult statisticalRevenueByDayResult = new StatisticalRevenueByDayResult();
            statisticalRevenueByDayResult.setDate(item.getDate());
            if (item.getPrice() != null) {
                statisticalRevenueByDayResult.setPrice(String.format("%.0f", item.getPrice()));
            } else {
                statisticalRevenueByDayResult.setPrice("0");
            }
            resultList.add(statisticalRevenueByDayResult);
        }
        return new BaseResponse(200, "Test", resultList);
    }

    @Override
    public BaseResponse ketQuaThongKeUtm(String startDate, String endDate, String jwt) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<String> utmCodeList = new ArrayList<>();
        List<KetQuaThongKeUtmDto> listResult = new ArrayList<>();
        if (account.getRole().equals("admin") || account.getRole().equals("marketing")) {
            listResult = dataRepository.thongKeUtmTheoThoiGian_admin(Long.parseLong(startDate), Long.parseLong(endDate));
        }
//        else if (account.getRole().equals("marketing")) {
//            List<UtmMedium> utmMediumList = utmMediumRepository.findAllByAccount(account);
//            if (utmMediumList.isEmpty()){
//                return new BaseResponse(200, "OK", null);
//            }
//            for (UtmMedium item : utmMediumList){
//                utmCodeList.add(item.getCode().toLowerCase());
//            }
//            listResult = dataRepository.thongKeUtmTheoThoiGian(Long.parseLong(startDate), Long.parseLong(endDate), utmCodeList);
//        }
        else {
            return new BaseResponse(200, "OK", null);
        }
        return new BaseResponse(200, "OK", listResult);
    }

    @Override
    public BaseResponse ketQuaThongKeTopUtm(String startDate, String endDate, String shopCode) {
        List<KetQuaThongKeTopUtmDto> dtos = dataRepository.thongKeDataCuaTaiKhoanTheoThoiGian(Long.parseLong(startDate), Long.parseLong(endDate), shopCode);
        List<String> accounts = dtos.stream().map(KetQuaThongKeTopUtmDto::getUserName).collect(Collectors.toList());
        List<Account> marketers = acountService.getMtkByShop(shopCode);
        for (Account e : marketers) {
            if (accounts.contains(e.getUserName())) continue;
            dtos.add(KetQuaThongKeTopUtmDto.builder()
                    .userName(e.getUserName())
                    .fullName(e.getFullName())
                    .count(0L)
                    .build());
        }

        dtos = dtos
                .stream()
                .sorted(Comparator.comparing(KetQuaThongKeTopUtmDto::getCount).reversed())
                .collect(Collectors.toList());
        return new BaseResponse(200, "OK", dtos);
    }

    @Override
    public BaseResponse statisticalRevenueByDate(String startDate, String endDate, String shopCode) {
        return new BaseResponse(200, "OK", dataRepository.statisticalRevenueByDate(Long.parseLong(startDate), Long.parseLong(endDate), shopCode));
    }

    @Override
    public BaseResponse statisticUtmByDate(String startDate, String endDate, String shopCode) {
        return new BaseResponse(200, "OK", dataRepository.statisticUtmByDate(Long.parseLong(startDate), Long.parseLong(endDate), shopCode));
    }

    @Override
    public BaseResponse statisticDataByDateAndStatus(String startDate, String endDate, String shopCode) {
        return new BaseResponse(200, "OK", dataRepository.statisticDataByDateAndStatus(Long.parseLong(startDate), Long.parseLong(endDate), shopCode));
    }

    @Override
    public BaseResponse statisticDataByDateAndStatusAndUser(String startDate, String endDate, String shopCode, String userId) {
        return new BaseResponse(200, "OK", dataRepository.statisticDataByDateAndStatusAndUser(Long.parseLong(startDate), Long.parseLong(endDate), shopCode, Long.parseLong(userId)));
    }

    @Override
    public BaseResponse statisticQuantityDataByDateAndStatus(String startDate, String endDate, String shopCode) {
        return new BaseResponse(200, "OK", dataRepository.statisticQuantityDataByDateAndStatus(Long.parseLong(startDate), Long.parseLong(endDate), shopCode));
    }

    @Override
    public BaseResponse getAllByPhone(String phone, String shopCode) {
        return new BaseResponse(200, "OK", dataRepository.findAllByPhoneAndShopCode(phone, shopCode));
    }

    @Override
    public BaseResponse resetCost(Double cost) {
//        List<Data> dataList = dataRepository.findAllByCostIsNotNull();
//        if (dataList.isEmpty()){
//            return new BaseResponse(200, "Ok", dataList);
//        }
//        Config config = configRepository.findAllByCode("CPVC");
//        List<Data> resultList =  dataList.stream().map(item -> {
//            if (item.getDateOnly() < config.getFromDate() || item.getDateOnly() > config.getToDate() ){
//                item.setCost(Double.parseDouble(config.getDefaultValue()));
//            }else {
//                item.setCost(Double.parseDouble(config.getValue()));
//            }
//            return item;
//        }).collect(Collectors.toList());
//        dataRepository.saveAll(resultList);
        return new BaseResponse(200, "Ok", null);
    }

    @Override
    public BaseResponse findUniqueShop(String startDate, String endDate, Long id) {
        return new BaseResponse(200, "Ok", dataRepository.findUniqueShop(Long.parseLong(startDate), Long.parseLong(endDate), id));
    }

    @Override
    public BaseResponse statisticCostByStatusData(String startDate, String endDate, String shopCode) {
        return new BaseResponse(200, "Ok", dataRepository.statisticCostByStatusData(Long.parseLong(startDate), Long.parseLong(endDate), shopCode));
    }

    @Override
    public BaseResponse searchByRole(String filter, String sort, int size, int page, String jwt) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        String[] sortList = sort.split(",");
        Sort.Direction direction = sortList[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortList[0]);
        if (account.getRole().equals("admin")) {
            Node rootNode = new RSQLParser().parse(filter);
            Specification<Data> spec = rootNode.accept(new CustomRsqlVisitor<Data>());
            return new BaseResponse(200, "Ok", dataRepository.findAll(spec, pageable));
        } else {
            filter = filter + ";account.id==" + account.getId();
            Node rootNode = new RSQLParser().parse(filter);
            Specification<Data> spec = rootNode.accept(new CustomRsqlVisitor<Data>());
            return new BaseResponse(200, "Ok", dataRepository.findAll(spec, pageable));
        }
    }

    @Override
    public BaseResponse updateStatusData(List<Data> datas) {
        List<Data> listData;
        try {
            listData = dataRepository.saveAll(datas);
        } catch (RuntimeException ex) {
            //logger.error(String.format("OrderServiceImpl|updateStatusData|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex);
        }

        return new BaseResponse(200, "Thành công", datas);
    }

    @Override
    public BaseResponse updateStatusDataList(List<Long> ids, int status) {
        try {
            dataRepository.findAllByIds(ids, status);
            return new BaseResponse(200, "Ok", "Ok");
        } catch (Exception e) {
            return new BaseResponse(500, "FAIL", e.getMessage());
        }
    }

    @Override
    public BaseResponse statisticRevenue(Long startDate, Long endDate, String shopCode) {
        return new BaseResponse().success(dataRepository.statisticRevenue(startDate, endDate, shopCode));
    }

    @Override
    public MarketingPerformanceDto getMarketingPerformanceReport(MarketingPerformanceReq req) {
        List<Data> allData = dataRepository.getByTimeAndShop(req.getStartDate(), req.getEndDate(), req.getShopCode());
        List<String> listUtm = this.getListUtm(allData);
        List<UtmMedium> utmMediums = utmMediumRepository.getByListUtm(listUtm);

        List<MarketingPerformanceDetail> reports = new ArrayList<>();

        Long startDate = DateUtil.getDate(req.getStartDate());
        Long endDate = DateUtil.getDate(req.getEndDate());

        List<Account> accounts = this.getAccounts(utmMediums);
        for (Account account : accounts) {
            if (!account.getRole().equals(ROLE_MKT)) continue;
            List<Cost> costList = costService.getByDateAndAccountAndShop(
                    startDate
                    , endDate
                    , account
                    , req.getShopCode()
            );

            List<String> utmOfAccount = this.getUtmByAccount(utmMediums, account);
            List<Data> accountData = this.getAllDataOfUtm(utmOfAccount, allData);
            List<Data> orderData = this.getOrder(accountData);

            MarketingPerformanceDetail dto = MarketingPerformanceDetail.builder()
                    .account(account.getUserName())
                    .fullName(account.getFullName())
                    .totalData(accountData.size())
                    .totalOrder(orderData.size())
                    .revenue(this.getRevenue(orderData))
                    .adsCost(this.getAdsCost(costList))
                    .build();

            dto.setAdsTotalData(dto.getAdsCost() / this.getDenominator(dto.getTotalData()));
            dto.setAdsTotalOrder(dto.getAdsCost() / this.getDenominator(dto.getTotalOrder()));
            dto.setAdsRevenue(getAdsCostRevenue(dto.getAdsCost(), dto.getRevenue()));
            dto.setTlc(dto.getTotalOrder() * 100 / this.getDenominator(dto.getTotalData()));

            reports.add(dto);
        }

        reports = this.addAllAccountOfShop(reports, req.getShopCode());

        MarketingPerformanceDto dto = MarketingPerformanceDto.builder()
                .details(reports)
                .sumData(getSumData(reports))
                .sumOrder(getSumOrder(reports))
                .sumRevenue(getSumRevenue(reports))
                .build();

        Double sumAds = this.getSumAdsCost(reports);

        dto.setTlc(dto.getSumOrder() * 100 / this.getDenominator(dto.getSumData()));
        dto.setAdsSumData(sumAds / this.getDenominator(dto.getSumData()));
        dto.setAdsSumOrder(sumAds / this.getDenominator(dto.getSumOrder()));
        dto.setAdsSumRevenue(this.getAdsCostRevenue(sumAds, dto.getSumRevenue()));
        dto.setSumAds(sumAds);
        return dto;
    }

    private List<MarketingPerformanceDetail> addAllAccountOfShop(List<MarketingPerformanceDetail> details, String shopCode) {
        List<String> existedAccount = this.getExistedAccount(details);
        List<Account> accounts = acountService.getMtkByShop(shopCode);
        accounts.forEach(e -> {
            if (existedAccount.contains(e.getUserName())) return;
            details.add(MarketingPerformanceDetail.get(e.getUserName(), e.getFullName()));
        });
        return details;
    }

    private List<String> getExistedAccount(List<MarketingPerformanceDetail> details) {
        return details.stream().map(e -> e.getAccount()).collect(Collectors.toList());
    }

    private Double getSumAdsCost(List<MarketingPerformanceDetail> details) {
        return details.stream()
                .filter(e -> e.getAdsCost() != null)
                .mapToDouble(e -> e.getAdsCost())
                .sum();
    }

    private Double getSumRevenue(List<MarketingPerformanceDetail> details) {
        return details.stream()
                .filter(e -> e.getRevenue() != null)
                .mapToDouble(e -> e.getRevenue())
                .sum();
    }

    private Double getSumAdsTotalOrder(List<MarketingPerformanceDetail> details) {
        return details.stream()
                .filter(e -> e.getAdsTotalOrder() != null)
                .mapToDouble(e -> e.getAdsTotalOrder())
                .sum();
    }

    private Double getSumAdsTotalData(List<MarketingPerformanceDetail> details) {
        return details.stream()
                .filter(e -> e.getAdsTotalData() != null)
                .mapToDouble(e -> e.getAdsTotalData())
                .sum();
    }

    private Long getSumOrder(List<MarketingPerformanceDetail> details) {
        return details.stream()
                .filter(e -> e.getTotalOrder() != null)
                .mapToLong(e -> e.getTotalOrder())
                .sum();
    }

    private Long getSumData(List<MarketingPerformanceDetail> details) {
        return details.stream()
                .filter(e -> e.getTotalData() != null)
                .mapToLong(e -> e.getTotalData())
                .sum();
    }

    private Double getAdsCostRevenue(Double adsCost, Double revenue) {
        if (revenue.equals(0.0)) return 100D;
        return adsCost / revenue * 100;
    }

    private Double getDenominator(Double n) {
        return n == 0 ? 1 : n;
    }

    private Double getDenominator(Integer i) {
        return i == 0D ? 1D : i;
    }

    private Double getDenominator(Long i) {
        return i == 0D ? 1D : i;
    }

    private Double getAdsCost(List<Cost> costList) {
        return costList.stream()
                .filter(e -> e.getCostPerDay() != null)
                .mapToDouble(e -> e.getCostPerDay())
                .sum();
    }

    private Double getRevenue(List<Data> orderData) {
        return orderData.stream()
                .filter(e -> e.getPrice() != null)
                .mapToDouble(e -> e.getPrice())
                .sum();
    }

    private List<Data> getOrder(List<Data> dataList) {
        return dataList.stream()
                .filter(e -> orderStatus.contains(e.getStatus()))
                .collect(Collectors.toList());
    }

    private List<Data> getAllDataOfUtm(List<String> listUtm, List<Data> allData) {
        return allData.stream()
                .filter(e -> e.getUtm_medium() != null)
                .filter(e -> listUtm.contains(e.getUtm_medium().toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<String> getUtmByAccount(List<UtmMedium> utmMediums, Account account) {
        return utmMediums.stream()
                .filter(e -> e.getAccount() != null)
                .filter(e -> e.getAccount().equals(account))
                .map(e -> e.getCode().toLowerCase())
                .collect(Collectors.toList());
    }

    private List<Account> getAccounts(List<UtmMedium> utmMediums) {
        return utmMediums.stream()
                .filter(e -> e.getAccount() != null)
                .map(UtmMedium::getAccount)
                .distinct().collect(Collectors.toList());
    }

    private List<String> getListUtm(List<Data> allData) {
        return allData.stream().map(e -> e.getUtm_medium().toLowerCase())
                .distinct()
                .collect(Collectors.toList());
    }

    public String getClientIp() throws Exception {
        String urlString = "http://checkip.amazonaws.com/";
        URL url = null;
        try {
            url = new URL(urlString);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public BaseResponse tracking(String code) {
        Data data = dataRepository.findAllByShippingCode(code);
        if (data == null){
            return new BaseResponse().fail("Không tồn tại bản ghi phù hợp!");
        }
        if (data.getShippingCode() == null){
            return new BaseResponse().fail("Đơn hàng không tồn tại mã vận đơn!");
        }
        if (data.getShippingAccount() == null){
            return new BaseResponse().fail("Đơn hàng không tồn tại tài khoản vận chuyển");
        }
        String url = data.getShippingAccount().getAccountShippingType().getUrl() + "/order/tracking";
//        String url = "https://api.ghsv.vn/v1/order/tracking";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", data.getShippingAccount().getToken());
        TrackingBodyGHSVRequest body = new TrackingBodyGHSVRequest(data.getShippingCode());
        HttpEntity<TrackingBodyGHSVRequest> request = new HttpEntity<>(body, headers);
        TrackingGHSVResponse response = restTemplate.postForObject(url, request, TrackingGHSVResponse.class);
        List<TrackingGHSVResult> results = new ArrayList<>();
        if (response.getSuccess()){
            for (TrackingGHSVResponse.Tracking item : response.getTrackingList()){
                TrackingGHSVResult trackingGHSVResult = new TrackingGHSVResult(item.getTime(), item.getStatus(), item.getAddress(), item.getNote());
                results.add(trackingGHSVResult);
            }
            return new BaseResponse().success(results);
        }

        return new BaseResponse().fail(response.getMsg());
    }

    @Override
    public UpdateStatusDataWebhookGHSVRP updateStatusGHSV(UpdateStatusDataWebhookGHSVRQ body) throws JsonProcessingException, NoSuchAlgorithmException {
        if (body.getRequiredCode() == null || body.getRequiredCode().equals("")) {
            return new UpdateStatusDataWebhookGHSVRP(Boolean.FALSE, "Mã đơn hàng không được để trống!");
        }
        if (body.getStatus() == null || body.getStatus().equals("")) {
            return new UpdateStatusDataWebhookGHSVRP(Boolean.FALSE, "Trạng thái không được để trống!");
        }
        Data data = dataRepository.findAllByShippingCode(body.getClientCode());
        if (data == null) {
            return new UpdateStatusDataWebhookGHSVRP(Boolean.FALSE, "Không có đơn nào trùng với mã vận đơn!");
        }
        if (data.getShippingStatus() == null) {
            data.setShippingStatus(body.getStatus());
        } else {
            data.setShippingStatus(data.getShippingStatus() + ";" + body.getStatus());
        }
        if (data.getStatus() == DATA.STATUS_SUCCESS && convertStatusShippingToData.gHSV(body.getStatus()) == DATA.CANCEL_ORDER){
            if (!statisticsGeneralService.checkStatisticsGeneral(data).isEmpty()){
                updateStatisticGeneralWhenCancelData(data);
            }
        }
        OrderGHSV orderGHSV = modelMapper.map(body, OrderGHSV.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        Date tempDate = new Date();
        LocalDateTime currentDate = tempDate.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
        Long now = Long.parseLong(currentDate.format(formatter));
        data.setLastUpdateTrackingTime(now);
        data.setStatus(convertStatusShippingToData.gHSV(body.getStatus()));
        data.setActualFee(Long.valueOf(body.getFee()));
        if (body.getStatus().equals("Đã Trả Hàng Toàn Bộ")){
            Long date = Long.parseLong(String.valueOf(data.getTimeOrderSuccess()).substring(0, 7));
            SalerKpiStatistics statistics = saleKpiStatisticRepository.findAllByDateAndSaler(date, data.getSaleAccount());
            if (statistics != null) {
                statistics.setTotalOrderSuccess(statistics.getTotalOrderSuccess() - 1);
                saleKpiStatisticRepository.save(statistics);
            }
        }
        if (body.getStatus().equals("Đã Giao Hàng")){
            Long date = Long.parseLong(String.valueOf(data.getTimeOrderSuccess()).substring(0,7));
            SalerKpiStatistics statistics=saleKpiStatisticRepository.findAllByDateAndSaler(date,data.getSaleAccount());
            if (statistics != null){
                statistics.setActualRevenue(statistics.getActualRevenue() + data.getPrice());
            }
        }

        if (data.getStatus() == DATA.DELIVERY_VERIFIED){
            data.setCogs(new Long(body.getFee()));
            data.setTimeShipping(now);
        }

        switch (body.getStatus()){
            case "Đang Chuyển Kho Giao": case "Đã Chuyển Kho Giao":
                successExportBoL(data);
                break;

            case "Xác Nhận Hoàn":
                createImportBoL(data);
                Config config = configRepository.findAllByCode("CPH"+data.getShopCode());
                Long cogs;
                if (config != null){
                    cogs = (data.getDateOnly() >= config.getFromDate() && data.getDateOnly()<= config.getToDate()) ? Long.parseLong(config.getValue()) : Long.parseLong(config.getDefaultValue());
                }else {
                    cogs = 0L;
                }
                data.setCogs(cogs);
                break;

            case "Đang Chuyển Kho Trả": case "Đã Chuyển Kho Trả Toàn Bộ": case "Đã Chuyển Kho Trả Một Phần": case "Đang Trả Hàng":
            case "Đã Trả Hàng Một Phần":
                confirmImportBoL(data);
                break;

            case "Đã Đối Soát Trả Hàng": case "Đã Trả Hàng Toàn Bộ":
                successImportBoL(data);
                break;

            case "Hủy":
                if (data.getBoL() != null){
                    BoL boL = data.getBoL();
                    if (boL.getType() == BoLStatus.BOL_EXPORT_TYPE){
                        if (boL.getStatus() == BoLStatus.BOL_CONFIRMED_STATUS || boL.getStatus() == BoLStatus.BOL_COMING_STATUS){
                            List<BoLDetail> boLDetailList = boLDetailRepository.findAllByBoL(boL);
                            for (BoLDetail item : boLDetailList){
                                item.getSubProduct().setAvailableQuantity(item.getSubProduct().getAvailableQuantity() + item.getTotalQuantity());
                                subProductRepository.save(item.getSubProduct());
                            }
                        }else if (boL.getType() == BoLStatus.BOL_SUCCESS_STATUS){
                            List<BoLDetail> boLDetailList = boLDetailRepository.findAllByBoL(boL);
                            for (BoLDetail item : boLDetailList){
                                item.getSubProduct().setInventoryQuantity(item.getSubProduct().getAvailableQuantity() + item.getTotalQuantity());
                                subProductRepository.save(item.getSubProduct());
                            }
                        }
                        boL.setStatus(BoLStatus.BOL_CANCEL_STATUS);
                        boLRepository.save(boL);
                    }

                    if (boL.getType() == BoLStatus.BOL_IMPORT_RETURN_GOODS){
                        if (boL.getType() == BoLStatus.BOL_EXPORT_TYPE){
                            if (boL.getStatus() == BoLStatus.BOL_CONFIRMED_STATUS || boL.getStatus() == BoLStatus.BOL_COMING_STATUS){
                                List<BoLDetail> boLDetailList = boLDetailRepository.findAllByBoL(boL);
                                for (BoLDetail item : boLDetailList){
                                    item.getSubProduct().setAwaitingProductQuantity(item.getSubProduct().getAwaitingProductQuantity() - item.getTotalQuantity());
                                    subProductRepository.save(item.getSubProduct());
                                }
                            }else if (boL.getType() == BoLStatus.BOL_SUCCESS_STATUS){
                                List<BoLDetail> boLDetailList = boLDetailRepository.findAllByBoL(boL);
                                for (BoLDetail item : boLDetailList){
                                    item.getSubProduct().setAvailableQuantity(item.getSubProduct().getAvailableQuantity() - item.getTotalQuantity());
                                    item.getSubProduct().setInventoryQuantity(item.getSubProduct().getInventoryQuantity() - item.getTotalQuantity());
                                    subProductRepository.save(item.getSubProduct());
                                }
                            }
                            boL.setStatus(BoLStatus.BOL_CANCEL_STATUS);
                            boLRepository.save(boL);
                        }
                    }

                }
                Long date = Long.parseLong(String.valueOf(data.getTimeOrderSuccess()).substring(0, 7));
                SalerKpiStatistics statistics = saleKpiStatisticRepository.findAllByDateAndSaler(date, data.getSaleAccount());
                if (statistics != null) {
                    statistics.setTotalOrderSuccess(statistics.getTotalOrderSuccess() - 1);
                    saleKpiStatisticRepository.save(statistics);
                }
        }
        
        //set order
        Order order = orderReponsitory.findAllByOrderCode(body.getClientCode());
        order.setStatus(convertStatusShippingToData.gHSV(body.getStatus()));
        order.setStatusText(body.getStatus());
        if (order == null){
            return new UpdateStatusDataWebhookGHSVRP(Boolean.FALSE, "Mã đơn hàng không được để trống!");
        }
        if (body.getStatus() == "Đang Vận Chuyển"){
            order.setDateApprove(now);
        }
        if (body.getStatus() == "Đã Giao Hàng"){
            order.setDateSuccess(now);
        }
        order.setDateChange(now);
        orderReponsitory.save(order);
        dataRepository.save(data);
        return new UpdateStatusDataWebhookGHSVRP(Boolean.TRUE, "");
    }


    public BaseResponse createExportBoL(Account account,  Data data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ProductShippingOrder> productShippingOrderList = objectMapper.readValue(data.getProductIds(), new TypeReference<List<ProductShippingOrder>>() {});
        BoL boL = BoL.builder()
                .creator(account)
                .shop(shopRepository.findAllByCode(data.getShopCode()).get(0))
                .type(BoLStatus.BOL_EXPORT_TYPE)
                .status(BoLStatus.BOL_CREATE_STATUS)
                .build();
        List<BoLDetail> boLDetailList = new ArrayList<>();
        for (ProductShippingOrder item : productShippingOrderList){
            List<SubProduct> subProduct = new ArrayList<>();
            subProduct.add(subProductRepository.findAllById(item.getProduct().getId()));
            boL.setWarehouse(item.getProduct().getWarehouse());
            BoLDetail boLDetail = BoLDetail.builder()
                    .subProduct(subProduct.get(0))
                    .quantity(item.getQuantity())
                    .totalQuantity(item.getQuantity())
                    .availableQuantity(0L)
                    .totalPrice(item.getPrice())
                    .price(data.getPrice().longValue())
                    .build();
            boLDetailList.add(boLDetail);
        }
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boL);
        createBoLRequest.setBoLDetailList(boLDetailList);
        BoLServiceImpl boLService1 = new BoLServiceImpl();
//                        boLService1.create(createBoLRequest);
        BaseResponse baseResponse = boLService.create(createBoLRequest);
        if (boLService.create(createBoLRequest).CODE == 500){
            return  baseResponse;
        }
        createBoLRequest = (CreateBoLRequest) baseResponse.RESULT;
        data.setBoL(createBoLRequest.getBoLDetailList().get(0).getBoL());
        return baseResponse;
    }

    public BaseResponse  createImportBoL(Data data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ProductShippingOrder> productShippingOrderList = objectMapper.readValue(data.getProductIds(), new TypeReference<List<ProductShippingOrder>>() {});
        BoL boL = BoL.builder()
                .creator(data.getSaleAccount())
                .shop(shopRepository.findAllByCode(data.getShopCode()).get(0))
                .type(BoLStatus.BOL_IMPORT_RETURN_GOODS)
                .status(BoLStatus.BOL_CREATE_STATUS)
                .note("Nhập hàng hoàn")
                .build();
        List<BoLDetail> boLDetailList = new ArrayList<>();
        for (ProductShippingOrder item : productShippingOrderList){
            List<SubProduct> subProduct = new ArrayList<>();
            subProduct.add(subProductRepository.findAllById(item.getProduct().getId()));
            boL.setWarehouse(item.getProduct().getWarehouse());
            BoLDetail boLDetail = BoLDetail.builder()
                    .subProduct(subProduct.get(0))
                    .quantity(item.getQuantity())
                    .totalQuantity(item.getQuantity())
                    .availableQuantity(0L)
                    .totalPrice(item.getPrice())
                    .note("Nhập hàng hoàn")
                    .build();
            boLDetailList.add(boLDetail);
        }
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boL);
        createBoLRequest.setBoLDetailList(boLDetailList);
        BoLServiceImpl boLService1 = new BoLServiceImpl();
//                        boLService1.create(createBoLRequest);
        BaseResponse baseResponse = boLService.create(createBoLRequest);
        if (boLService.create(createBoLRequest).CODE == 500){
            return  baseResponse;
        }
        createBoLRequest = (CreateBoLRequest) baseResponse.RESULT;
        data.setBoL(createBoLRequest.getBoLDetailList().get(0).getBoL());
        return baseResponse;
    }

    private void successExportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_SUCCESS_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
                .note(boL.getNote())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
    }

    private void cancelExportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_CANCEL_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
    }
    private void confirmImportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_CONFIRMED_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
                .note(boL.getNote())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
        commingImportBoL(data);
    }

    private void commingImportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_COMING_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
                .note(boL.getNote())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
    }

    private void successImportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_SUCCESS_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
                .note(boL.getNote())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
        data.setShippingCode("");
    }

    private void createStatisticGeneral(Data data) throws JsonProcessingException {
        Shop shop = shopRepository.findShopByCode(data.getShopCode());
        StatisticsGeneral statisticsGeneral = new StatisticsGeneral();
        statisticsGeneral.setDate(data.getDateOnly());
        statisticsGeneral.setCostPrice(data.getTotalProductValue());
        statisticsGeneral.setShippingCost(data.getCost().longValue());
        statisticsGeneral.setSales(data.getPrice().longValue());
        statisticsGeneral.setTotalOrder(1L);
        statisticsGeneral.setShop(shop);
        statisticsGeneralService.create(statisticsGeneral);
    }

    private void updateStatisticGeneral(Data data) throws JsonProcessingException, NoSuchAlgorithmException {
        Data realData = dataRepository.findAllById(data.getId());
        List<Shop> shop = shopRepository.findAllByCode(realData.getShopCode());
        List<StatisticsGeneral> statisticsGeneral = statisticsGeneralRepository.findAllByRangeDate(realData.getDateOnly(), realData.getDateOnly(), shop.get(0).getId());
        statisticsGeneral.get(0).setCostPrice(statisticsGeneral.get(0).getCostPrice() + data.getTotalProductValue());
        Long shippingCost = 0L;
        if (data.getCost() != null){
            shippingCost = data.getCost().longValue();
        }
        statisticsGeneral.get(0).setShippingCost(statisticsGeneral.get(0).getShippingCost() + shippingCost);
        statisticsGeneral.get(0).setSales(statisticsGeneral.get(0).getSales() + data.getPrice().longValue());
        statisticsGeneral.get(0).setTotalOrder(statisticsGeneral.get(0).getTotalOrder() + 1L);
        statisticsGeneralService.update(statisticsGeneral.get(0).getId(), statisticsGeneral.get(0));
    }

    private void updateStatisticGeneralWhenCancelData(Data data) throws NoSuchAlgorithmException, JsonProcessingException {
        Data realData = dataRepository.findAllById(data.getId());
        List<Shop> shop = shopRepository.findAllByCode(realData.getShopCode());
        StatisticsGeneral statisticsGeneral = statisticsGeneralRepository.findAllByRangeDate(realData.getDateOnly(), realData.getDateOnly(), shop.get(0).getId()).get(0);
        statisticsGeneral.setCostPrice(statisticsGeneral.getCostPrice() - realData.getTotalProductValue());
        Long shippingCost = 0L;
        if (realData.getCost() != null){
            shippingCost = realData.getCost().longValue();
        }
        statisticsGeneral.setShippingCost(statisticsGeneral.getShippingCost() - shippingCost);
        statisticsGeneral.setSales(statisticsGeneral.getSales() - realData.getPrice().longValue());
        statisticsGeneral.setTotalOrder(statisticsGeneral.getTotalOrder() - 1L);
        statisticsGeneralService.update(statisticsGeneral.getId(), statisticsGeneral);
    }
}
