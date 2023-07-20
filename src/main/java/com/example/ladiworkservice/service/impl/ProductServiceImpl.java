package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.global_variable.BoLStatus;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.GetCostAndRevenueReponse;
import com.example.ladiworkservice.controller.request.*;
import com.example.ladiworkservice.dto.ProductDtoResponse;
import com.example.ladiworkservice.query.CustomRsqlVisitor;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.ProductService;
import com.example.ladiworkservice.controller.request.CreateProductRequest;
import com.example.ladiworkservice.controller.request.UpdateSoldProductRequest;
import com.example.ladiworkservice.model.*;
import com.example.ladiworkservice.repository.*;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class    ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    BoLRepository boLRepository;
    @Autowired
    BoLDetailRepository boLDetailRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubProductRepository subProductRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SubProductServiceImpl subProductService;

    @Autowired
    ShopRepository shopRepository;

    @Override
    public BaseResponse getAllByShopcodeAndStatus (String shopcode, int status) {

        List<Product> productList = new ArrayList<>();
        productList = productRepository.findAllByShopcodeAndStatus(shopcode, status);
        return new BaseResponse(200, "OK", productList);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public BaseResponse create(CreateProductRequest createProductRequest) {
        Pattern p = Pattern.compile("^[A-Za-z0-9._-]{1,30}$");
        Matcher m = p.matcher(createProductRequest.getProduct().getCode());
        boolean b = m.find();
        if (!b){
            return new BaseResponse(500, "FAIL", "Code sản phẩm không được có kí tự đặc biệt và có độ dài 1-30 kí tự!");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime currentDate = LocalDateTime.now();
        Long now = Long.parseLong(currentDate.format(formatter));
        Shop shop = shopRepository.findAllById(createProductRequest.getProduct().getShop().getId());
        if (createProductRequest.getProduct().getId() == null){
            createProductRequest.getProduct().setCreateAt(now);
            List<Product> productList = productRepository.findAllByCodeAndShop(createProductRequest.getProduct().getCode(), shop);
            if (!productList.isEmpty()){
                return new BaseResponse(500, "FAIL", "Mã sản phẩm đã tồn tại");
            }
        }
        createProductRequest.getProduct().setUpdateAt(now);
        List<SubProduct> subProductList = new ArrayList<>();
        Product product = productRepository.save(createProductRequest.getProduct());
        for (SubProduct item : createProductRequest.getSubProductList()){
            if (item.getAvailableQuantity()<0L || item.getTotalQuantity() < 0L
                    || item.getInventoryQuantity() < 0L || item.getDefectiveProductQuantity() < 0L
                    || item.getInventoryQuantity() < 0L || item.getWeight() < 0.0 || item.getHigh() < 0
                    || item.getWide() < 0 || item.getLength() < 0){
                return new BaseResponse(500, "FAIL", "Lỗi");
            }
            Pattern sp = Pattern.compile("^[A-Za-z0-9._-]{1,40}$");
            Matcher sm = sp.matcher(createProductRequest.getProduct().getCode());
            boolean sb = sm.find();
            if (!sb){
                return new BaseResponse(500, "FAIL", "Code mẫu mã không được có kí tự đặc biệt và có độ dài 1-40 kí tự!");
            }
            if (item.getId() == null){
                item.setCreateAt(now);
                subProductList.add(item);
                Warehouse warehouse = warehouseRepository.findAllById(createProductRequest.getProduct().getWarehouseId());
                item.setWarehouse(warehouse);
                List<SubProduct> subProducts = subProductRepository.findAllByPropertiesAndProduct(item.getProperties(), product);
                if(!subProducts.isEmpty()){
                    return new BaseResponse(500, "FAIL", "mẫu mã ko đc trùng thuộc tính");
                }
                List<SubProduct> checkCodeSubProduct = subProductRepository.findAllByCodeAndProduct(item.getCode(), product);
                if (!checkCodeSubProduct.isEmpty()){
                    return new BaseResponse(500, "FAIL", "Mã mẫu mã đã tồn tại");
                }
            }
            item.setUpdateAt(now);

        }
        return saveProduct(createProductRequest, subProductList, product);
    }

    @Override
    public BaseResponse search(String filter, String sort, int size, int page) {
        Node rootNode = new RSQLParser().parse(filter);
        Specification<Product> spec = rootNode.accept(new CustomRsqlVisitor<Product>());
        String[] sortList = sort.split(",");
        Sort.Direction direction = sortList[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortList[0]);
        List<ProductDtoResponse> results = new ArrayList<>();
        Page<Product> productPage = productRepository.findAll(spec, pageable);
        List<Product> productList = productPage.toList();
        for (Product item : productList){
            ProductDtoResponse productDtoResponse = modelMapper.map(item, ProductDtoResponse.class);
            List<SubProduct> subProductList = subProductRepository.findAllByProduct(item);
            productDtoResponse.setSubProductList(subProductList);
            results.add(productDtoResponse);
        }
        Page<ProductDtoResponse> pageResult = new PageImpl<>(results,pageable,productPage.getTotalElements());
        return new BaseResponse(200, "Ok", pageResult);
    }

    @Override
    public BaseResponse updateSoldProduct(List<UpdateSoldProductRequest> ids) {
        GetCostAndRevenueReponse getCostAndRevenueReponse = new GetCostAndRevenueReponse();
        List<SubProduct> subProductList = new ArrayList<>();
        for (UpdateSoldProductRequest item : ids){
            for (Long i = 0L ; i<item.getQuantity(); i++){
                SubProduct subProduct = subProductRepository.findAllById(item.getId());
                if (subProduct.getAvailableQuantity() <= 0){
                    return new BaseResponse(500, "Sản phẩm " + subProduct.getCode() + " Đã hết hàng không thể cập nhật đơn!", "Sản phẩm " + subProduct.getCode() + " Đã hết hàng không thể cập nhật đơn!");
                }
                BoLDetail boLDetail = boLDetailRepository.checkPriceImport(item.getId(), subProduct.getSold());
                getCostAndRevenueReponse.setCostImport(getCostAndRevenueReponse.getCostImport() + boLDetail.getPrice());
                subProductRepository.save(subProduct);
            }
        }
        return new BaseResponse(200,"Ok", getCostAndRevenueReponse);
    }

    @Override
    public BaseResponse statisticProduct(Long shopId) {
        return new BaseResponse(200, "Ok", productRepository.statisticProduct(shopId));
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public BaseResponse saveProduct(CreateProductRequest createProductRequest, List<SubProduct> subProductList, Product product) {
        try {
//            Product product = productRepository.save(createProductRequest.getProduct());
            for (SubProduct item : createProductRequest.getSubProductList()){
                item.setProduct(product);
            }
            subProductRepository.saveAll(createProductRequest.getSubProductList());
            List<BoLDetail> boLDetailList = new ArrayList<>();
            for (SubProduct item : subProductList){
                BoL boL = BoL.builder()
                        .type(BoLStatus.BOL_IMPORT_TYPE)
                        .status(BoLStatus.BOL_SUCCESS_STATUS)
                        .warehouse(item.getWarehouse())
                        .shop(product.getShop())
                        .createAt(product.getCreateAt())
                        .updateAt(product.getUpdateAt())
                        .build();
                boL = boLRepository.save(boL);
                BoLDetail boLDetail = BoLDetail.builder()
                        .boL(boL)
                        .subProduct(item)
                        .totalImport(item.getAvailableQuantity())
                        .availableQuantity(item.getAvailableQuantity())
                        .updateAt(product.getCreateAt())
                        .createAt(product.getCreateAt())
                        .totalQuantity(item.getTotalQuantity())
                        .price(item.getLastImportedPrice())
                        .totalPrice(item.getTotalQuantity() * item.getLastImportedPrice())
                        .build();
                boLDetailList.add(boLDetail);
            }
            boLDetailRepository.saveAll(boLDetailList);
            return new BaseResponse(200, "Ok", createProductRequest);
        }catch (Exception e){
            return new BaseResponse(500, "FAIL", "ERROR!");
        }
    }



}
