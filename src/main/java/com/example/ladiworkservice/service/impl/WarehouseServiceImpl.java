package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.global_variable.WAREHOUSE;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.SearchWarehouseReponse;
import com.example.ladiworkservice.controller.request.CreateWarehouseRequest;
import com.example.ladiworkservice.query.CustomRsqlVisitor;
import com.example.ladiworkservice.repository.AccountWarehouseRepository;
import com.example.ladiworkservice.repository.WarehouseRepository;
import com.example.ladiworkservice.service.WarehouseService;
import com.example.ladiworkservice.model.AccountWarehouse;
import com.example.ladiworkservice.model.Warehouse;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    AccountWarehouseRepository accountWarehouseRepository;

    @Autowired
    ModelMapper modelMapper;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public BaseResponse create(CreateWarehouseRequest createWarehouseRequest){
        if (createWarehouseRequest.getWarehouse().getId() != null){
            if (warehouseRepository.findAllById(createWarehouseRequest.getWarehouse().getId()).getFlag() == WAREHOUSE.FLAG_NOMAL){
                createWarehouseRequest.getWarehouse().setFlag(WAREHOUSE.FLAG_NOMAL);
                return saveWarehouse(createWarehouseRequest);
            }else {
                createWarehouseRequest.getWarehouse().setFlag(WAREHOUSE.FLAG_NONE_NOMAL);
                return saveWarehouse(createWarehouseRequest);
            }
        }else {
            createWarehouseRequest.getWarehouse().setFlag(WAREHOUSE.FLAG_NONE_NOMAL);
            return saveWarehouse(createWarehouseRequest);
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public BaseResponse saveWarehouse(CreateWarehouseRequest createWarehouseRequest){
        try {
            if (createWarehouseRequest.getWarehouse().getShop() == null ){
                return new BaseResponse(500, "FAIL", "Shop không được để trống!");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime currentDate = LocalDateTime.now();
            Long now = Long.parseLong(currentDate.format(formatter));
            createWarehouseRequest.getWarehouse().setCode(createWarehouseRequest.getWarehouse().getName() + " " + currentDate);
            List<AccountWarehouse> rejectStaffIdList = new ArrayList<>();
            List<AccountWarehouse> resolvetaffIdList = new ArrayList<>();
            Set<Long> staffSet = new HashSet<Long>(createWarehouseRequest.getStaffIdList());
            createWarehouseRequest.setStaffIdList(new ArrayList<Long>(staffSet));
            if (createWarehouseRequest.getWarehouse().getId() == null)
                createWarehouseRequest.getWarehouse().setCreateAt(now);
            createWarehouseRequest.getWarehouse().setUpdateAt(now);
            Warehouse warehouse = warehouseRepository.save(createWarehouseRequest.getWarehouse());
            checkStaffOfWarehouse(resolvetaffIdList, rejectStaffIdList, createWarehouseRequest.getStaffIdList(), warehouse.getId());
            return new BaseResponse(200, "Ok", createWarehouseRequest);

        }catch(Exception e) {
            return new BaseResponse(500, "FAIL", e.getMessage());
        }
    }

    @Override
    public BaseResponse search(String filter, String sort, int size, int page) {
        Node rootNode = new RSQLParser().parse(filter);
        Specification<Warehouse> spec = rootNode.accept(new CustomRsqlVisitor<Warehouse>());
        String[] sortList = sort.split(",");
        Sort.Direction direction = sortList[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortList[0]);
        List<SearchWarehouseReponse> results = new ArrayList<>();
        Page<Warehouse> warehousePage = warehouseRepository.findAll(spec,pageable);
        List<Warehouse> warehouseList = warehousePage.toList();
        for (Warehouse item : warehouseList){
            SearchWarehouseReponse searchWarehouseReponse = modelMapper.map(item, SearchWarehouseReponse.class);
            List<AccountWarehouse> accountWarehouses = accountWarehouseRepository.findAllByWarehouseIdAndStatus(item.getId(), 1);
            List<Long> staffIdList = accountWarehouses.stream().map(i -> i.getAccountId()).collect(Collectors.toList());
            searchWarehouseReponse.setStaffIdList(staffIdList);
            results.add(searchWarehouseReponse);
        }
        Page<SearchWarehouseReponse> pageResult = new PageImpl<>(results,pageable,warehousePage.getTotalElements());
        return new BaseResponse(200, "Ok", pageResult);
    }

    @Override
    public BaseResponse statisticWarehouse(Long warehouseId) {
        return new BaseResponse(200, "Ok", warehouseRepository.statisticProduct(warehouseId));
    }

    public void checkStaffOfWarehouse(List<AccountWarehouse> resolvetaffIdList, List<AccountWarehouse> rejectStaffIdList, List<Long> staffIdList, Long warehouseId){
        List<AccountWarehouse> oldStaffList = accountWarehouseRepository.findAllByWarehouseId(warehouseId);
        if (oldStaffList.isEmpty()){
            for (Long item: staffIdList){
                AccountWarehouse accountWarehouse = AccountWarehouse.builder()
                        .accountId(item)
                        .warehouseId(warehouseId)
                        .status(1)
                        .build();
                resolvetaffIdList.add(accountWarehouse);
            }
            accountWarehouseRepository.saveAll(resolvetaffIdList);
        }else {
            List<AccountWarehouse> temp = new ArrayList<>();
            for (AccountWarehouse accountWarehouse : oldStaffList){
                if (!staffIdList.contains(accountWarehouse.getAccountId())){
                    accountWarehouse.setStatus(-1);
                    rejectStaffIdList.add(accountWarehouse);
                    staffIdList.remove(accountWarehouse.getAccountId());
                }else {
                    accountWarehouse.setStatus(1);
                    temp.add(accountWarehouse);
                    staffIdList.remove(accountWarehouse.getAccountId());
                }
            }
            for (Long item: staffIdList){
                AccountWarehouse accountWarehouse = AccountWarehouse.builder()
                        .accountId(item)
                        .warehouseId(warehouseId)
                        .status(1)
                        .build();
                resolvetaffIdList.add(accountWarehouse);
            }

            accountWarehouseRepository.saveAll(temp);
            accountWarehouseRepository.saveAll(rejectStaffIdList);
            accountWarehouseRepository.saveAll(resolvetaffIdList);
        }

    }

    @Override
    public BaseResponse deleteById(Long id) {
        Warehouse warehouse = warehouseRepository.findAllById(id);
        if (warehouse == null){
            return new BaseResponse(500, "FAIL", "Không tồn tại kho với id tương ứng");
        }
        if (warehouse.getFlag() == WAREHOUSE.FLAG_NOMAL){
            return new BaseResponse(500, "FAIL", "Không thể xóa kho mặc định");
        }
        warehouse.setStaus(WAREHOUSE.STATUS_LOCK);
        warehouseRepository.save(warehouse);
        return new BaseResponse(200, "Ok", warehouse);
    }

}
