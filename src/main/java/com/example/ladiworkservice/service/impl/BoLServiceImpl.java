package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.global_variable.BoLStatus;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateBoLRequest;
import com.example.ladiworkservice.model.BoL;
import com.example.ladiworkservice.model.BoLDetail;
import com.example.ladiworkservice.model.SubProduct;
import com.example.ladiworkservice.query.CustomRsqlVisitor;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.BoLService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoLServiceImpl implements BoLService {
    @Autowired
    BoLRepository boLRepository;

    @Autowired
    BoLDetailRepository boLDetailRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SubProductRepository subProductRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public BaseResponse create(CreateBoLRequest createBoLRequest) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime currentDate = LocalDateTime.now();
            Long now = Long.parseLong(currentDate.format(formatter));
            if (createBoLRequest.getBoL().getId() == null) {
                createBoLRequest.getBoL().setCreateAt(now);
            }
            createBoLRequest.getBoL().setUpdateAt(now);
            List<SubProduct> subProductList = new ArrayList<>();
            if (createBoLRequest.getBoL().getId() != null){
                BoL boL = boLRepository.findAllById(createBoLRequest.getBoL().getId());
                if (boL == null){
                    return new BaseResponse(500,"FAIL", "FAIL");
                }else {
                    if (boL.getStatus() == BoLStatus.BOL_SUCCESS_STATUS || ( boL.getStatus() == BoLStatus.BOL_COMING_STATUS && createBoLRequest.getBoL().getStatus() != BoLStatus.BOL_SUCCESS_STATUS)){
                        return new BaseResponse(500, "FAIL", "Đơn này không thể được cập nhật!");
                    }

                    if (createBoLRequest.getBoL().getStatus() - boL.getStatus() > 1 && createBoLRequest.getBoL().getStatus() != BoLStatus.BOL_CANCEL_STATUS){
                        return new BaseResponse(500, "FAIL", "Đơn hàng phải cập nhật theo từng bước!");
                    }

                    if (createBoLRequest.getBoL().getStatus() - boL.getStatus() < 0){
                        return new BaseResponse(500, "FAIL", "Không thể quay lại bước trước đó !");
                    }
                }
            }
            for (BoLDetail item : createBoLRequest.getBoLDetailList()) {
                if (item.getTotalQuantity() < item.getAvailableQuantity()){
                    return new BaseResponse(500, "FAIL", "Số lượng sản phẩm không thể nhỏ hơn số lượng sản phẩm có thể bán!");
                }
                if (item.getId() != null){
                    BoLDetail temp = boLDetailRepository.findAllById(item.getId());
                }

                if (item.getPrice() != null){
                    item.setTotalPrice(item.getPrice() * item.getTotalQuantity());
                }
                SubProduct subProduct = subProductRepository.findAllById(item.getSubProduct().getId());
                switch (createBoLRequest.getBoL().getStatus()){
                    case BoLStatus.BOL_CREATE_STATUS:
                        switch (createBoLRequest.getBoL().getType()){
                            case BoLStatus.BOL_EXPORT_TYPE:
                                if (item.getTotalQuantity() > subProduct.getAvailableQuantity()){
                                    return new BaseResponse(500, "FAIL", subProduct.getCode() + " còn "+ subProduct.getAvailableQuantity() + " mẫu mã không đủ để xuất kho!");
                                }
                                break;
                            case BoLStatus.BOL_EXPORT_RETURN_GOODS:
                                if (item.getTotalQuantity() > subProduct.getAvailableQuantity()){
                                    return new BaseResponse(500, "FAIL", subProduct.getCode() + " còn "+ subProduct.getAvailableQuantity() + " mẫu mã không đủ để xuất kho!");
                                }
                                break;
                        }
                        break;
                    case BoLStatus.BOL_CONFIRMED_STATUS:
                        switch (createBoLRequest.getBoL().getType()){
                            case BoLStatus.BOL_IMPORT_TYPE:
                                subProduct.setAwaitingProductQuantity(item.getTotalQuantity() + subProduct.getAwaitingProductQuantity());
                                break;
                            case BoLStatus.BOL_EXPORT_RETURN_GOODS:
                                subProduct.setAvailableQuantity(subProduct.getAvailableQuantity() - item.getTotalQuantity());
                                subProduct.setSold(subProduct.getSold()+item.getTotalQuantity());
                                subProduct.setDefectiveProductQuantity(subProduct.getDefectiveProductQuantity() + item.getTotalQuantity());
                                break;
                            case BoLStatus.BOL_IMPORT_RETURN_GOODS:
                                subProduct.setAwaitingProductQuantity(subProduct.getAwaitingProductQuantity() + item.getTotalQuantity());
                                break;
                            case BoLStatus.BOL_EXPORT_TYPE:
                                subProduct.setAvailableQuantity(subProduct.getAvailableQuantity() - item.getTotalQuantity());
                                subProduct.setSold(subProduct.getSold()+item.getTotalQuantity());
                                break;
                        }
                        break;
                    case BoLStatus.BOL_COMING_STATUS:
                        break;
                    case BoLStatus.BOL_SUCCESS_STATUS:
                        switch (createBoLRequest.getBoL().getType()){
                            case BoLStatus.BOL_IMPORT_TYPE:
                                subProduct.setAwaitingProductQuantity(subProduct.getAwaitingProductQuantity() - item.getTotalQuantity());
                                subProduct.setAvailableQuantity(subProduct.getAvailableQuantity() + item.getAvailableQuantity());
                                subProduct.setInventoryQuantity(subProduct.getInventoryQuantity() + item.getTotalQuantity());
                                subProduct.setTotalQuantity(subProduct.getTotalQuantity() + item.getTotalQuantity());
                                subProduct.setLastImportedPrice(item.getPrice());
                                BoLDetail boLDetail = boLDetailRepository.checkTotalImport(subProduct.getId());
                                item.setTotalImport(boLDetail.getTotalImport() + item.getAvailableQuantity());
                                boLDetailRepository.save(boLDetail);
                                break;
                            case BoLStatus.BOL_EXPORT_RETURN_GOODS:
                                subProduct.setDefectiveProductQuantity(subProduct.getDefectiveProductQuantity() - item.getTotalQuantity());
                                subProduct.setInventoryQuantity(subProduct.getInventoryQuantity() - item.getTotalQuantity());
                                break;
                            case BoLStatus.BOL_IMPORT_RETURN_GOODS:
                                subProduct.setAwaitingProductQuantity(subProduct.getAwaitingProductQuantity() - item.getTotalQuantity());
                                subProduct.setAvailableQuantity(subProduct.getAvailableQuantity() + item.getTotalQuantity());
                                subProduct.setInventoryQuantity(subProduct.getInventoryQuantity() + item.getTotalQuantity());
                                break;
                            case BoLStatus.BOL_EXPORT_TYPE:
                                subProduct.setInventoryQuantity(subProduct.getInventoryQuantity() - item.getTotalQuantity());
                                break;
                            case BoLStatus.BOL_EXPORT_BY_DATA:
                                subProduct.setInventoryQuantity(subProduct.getInventoryQuantity() - item.getTotalQuantity());
                                break;
                        }
                        break;
                    case BoLStatus.BOL_CANCEL_STATUS:
                        switch (createBoLRequest.getBoL().getType()){
                            case BoLStatus.BOL_IMPORT_TYPE:
                                if (item.getId() != null){
                                    BoLDetail temp = boLDetailRepository.findAllById(item.getId());
                                    if (temp.getBoL().getStatus() == BoLStatus.BOL_CONFIRMED_STATUS){
                                        subProduct.setAwaitingProductQuantity(subProduct.getAwaitingProductQuantity() - item.getTotalQuantity());
                                    }
                                }
                                break;
                            case BoLStatus.BOL_EXPORT_RETURN_GOODS:
                                if (item.getId() != null){
                                    BoLDetail temp = boLDetailRepository.findAllById(item.getId());
                                    if (temp.getBoL().getStatus() == BoLStatus.BOL_CONFIRMED_STATUS){
                                        subProduct.setAvailableQuantity(subProduct.getAvailableQuantity() + item.getTotalQuantity());
                                        subProduct.setDefectiveProductQuantity(subProduct.getDefectiveProductQuantity() - item.getTotalQuantity());
                                    }
                                }
                                break;
                            case BoLStatus.BOL_IMPORT_RETURN_GOODS:
                                if (item.getId() != null){
                                    BoLDetail temp = boLDetailRepository.findAllById(item.getId());
                                    if (temp.getBoL().getStatus() == BoLStatus.BOL_CONFIRMED_STATUS){
                                        subProduct.setAwaitingProductQuantity(subProduct.getAwaitingProductQuantity() - item.getTotalQuantity());
                                    }
                                }
                                break;
                            case BoLStatus.BOL_EXPORT_TYPE:
                                if (item.getId() != null){
                                    BoLDetail temp = boLDetailRepository.findAllById(item.getId());
                                    if (temp.getBoL().getStatus() == BoLStatus.BOL_CONFIRMED_STATUS){
                                        subProduct.setAvailableQuantity(subProduct.getAvailableQuantity()+item.getTotalQuantity());
                                        subProduct.setSold(subProduct.getSold() - item.getTotalQuantity());
                                    }
                                }
                                break;
                        }
                        break;
                }
            }
            BoL boL = boLRepository.save(createBoLRequest.getBoL());
            for (BoLDetail item : createBoLRequest.getBoLDetailList()){
                item.setBoL(boL);
            }
            subProductRepository.saveAll(subProductList);
            boLDetailRepository.saveAll(createBoLRequest.getBoLDetailList());
            return new BaseResponse(200, "Ok", createBoLRequest);
        }catch (Exception e){
            return new BaseResponse(500, "FAIL", e.getMessage());
        }
    }

    @Override
    public BaseResponse search(String filter, String sort, int size, int page) {
        Node rootNode = new RSQLParser().parse(filter);
        Specification<BoL> spec = rootNode.accept(new CustomRsqlVisitor<BoL>());
        String[] sortList = sort.split(",");
        Sort.Direction direction = sortList[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortList[0]);
        return new BaseResponse(200, "Ok", boLRepository.findAll(spec, pageable));
    }

    @Override
    public BaseResponse statisticBoLByStatus(int type, String shopCode) {
        return new BaseResponse(200, "Ok", boLRepository.statisticBoLByStatus(type, shopCode));
    }

    @Override
    public BaseResponse statisticBoL(int type, Long shopCode) {
        return new BaseResponse(200, "Ok", boLRepository.statisticBoL(type, shopCode));
    }

    @Override
    public BaseResponse statisticHistoryBoL(String shopCode) {
        return new BaseResponse(200, "Ok", boLRepository.statisticHistoryBoL(shopCode));
    }

}
