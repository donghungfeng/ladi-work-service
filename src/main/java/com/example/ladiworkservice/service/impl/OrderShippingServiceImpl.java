package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.JwtTokenProvider;
import com.example.ladiworkservice.configurations.global_variable.BoLStatus;
import com.example.ladiworkservice.configurations.global_variable.SHIPPINGTYPE;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.CreateListOrderGHSVResponse;
import com.example.ladiworkservice.controller.reponse.ProductShippingOrder;
import com.example.ladiworkservice.shippingorders.ghn.request.Item;
import com.example.ladiworkservice.shippingorders.ghn.request.OPTIONGHN;
import com.example.ladiworkservice.shippingorders.ghn.request.ShippingOrdersRequest;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.ShippingOrderGHSVCreateReponse;
import com.example.ladiworkservice.shippingorders.ghn.reponse.ShippingOrderGHNCreateReponse;
import com.example.ladiworkservice.controller.request.CreateBoLRequest;
import com.example.ladiworkservice.controller.request.ghsvRequest.ShippingOrderGHNCreateBody;
import com.example.ladiworkservice.controller.request.ghsvRequest.ShippingOrderItem;
import com.example.ladiworkservice.shippingorders.ghn.request.*;
import com.example.ladiworkservice.shippingorders.ghsv.request.OPTIONGHSV;
import com.example.ladiworkservice.shippingorders.ghsv.request.ShippingOrderGHSVCreateBody;
import com.example.ladiworkservice.dto.OrderDto;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.BoLService;
import com.example.ladiworkservice.service.OrderShippingService;
import com.example.ladiworkservice.model.*;
import com.example.ladiworkservice.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderShippingServiceImpl extends BaseServiceImpl<OrderShipping> implements OrderShippingService {
    @Autowired
    AccountShippingTypeRepository accountShippingTypeRepository;

    @Autowired
    BoLDetailRepository boLDetailRepository;

    @Autowired
    StatisticsGeneralRepository statisticsGeneralRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AccountShippingRepository accountShippingRepository;
    @Autowired
    SubProductRepository subProductRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BoLService boLService;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OrderReponsitory oReponsitory;
    private final OrderShippingRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final DataRepository dataRepository;
    Logger logger = LoggerFactory.getLogger(OrderShippingServiceImpl.class);

    public OrderShippingServiceImpl(OrderShippingRepository orderRepository, OrderProductRepository orderProductRepository, DataRepository dataRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.dataRepository = dataRepository;
    }

    @Override
    protected BaseRepository<OrderShipping> getRepository() {
        return orderRepository;
    }

    @Override
    public CreateListOrderGHSVResponse createOrderFromGHSV(Object orderGHSV) {
        CreateListOrderGHSVResponse response = new CreateListOrderGHSVResponse(HttpStatus.OK.value(), "Tạo đơn hàng thành công", null, 0 ,0);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OrderDto orderDto = objectMapper.convertValue(orderGHSV, OrderDto.class);
            OrderShipping newOrder = mapToOrder(orderDto);

            var order = orderRepository.save(newOrder);
//            for(Long productId : orderDto.getProductIds()) {
//                orderProductRepository.createOrderProduct(order.getId(), productId);
//            }
            response.RESULT = order;
        }
        catch (RuntimeException ex) {
            logger.error(String.format("OrderServiceImpl|createOrderFromGHSV|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex);
        }

        return response;
    }

    private List<Data> updateStatusData(List<Data> datas) {
        List<Data> listData;
        try {
            listData = dataRepository.saveAll(datas);
        }
        catch (RuntimeException ex) {
            logger.error(String.format("OrderServiceImpl|updateStatusData|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex);
        }

        return listData;
    }

    private OrderShipping mapToOrder(OrderDto orderDto) {
        OrderShipping order = new OrderShipping();

        order.setOrderCode(orderDto.getRequiredCode());
        order.setClientCode(orderDto.getClientCode());
        order.setNote(orderDto.getNote());
        order.setProductNames(orderDto.getProduct());
        order.setWeight(orderDto.getWeight());
        order.setWidth(orderDto.getWidth());
        order.setLength(orderDto.getLength());
        order.setTotalOrderValue(orderDto.getValue());
        order.setReturn(orderDto.isReturn());
        order.setToName(orderDto.getToName());
        order.setToPhone(orderDto.getToPhone());
        order.setToProvince(orderDto.getToProvince());
        order.setToDistrict(orderDto.getToDistrict());
        order.setToWard(orderDto.getToWard());

        return order;
    }

    @Override
    public BaseResponse shippingOrders(ShippingOrdersRequest shippingOrdersRequest, String jwt) throws JsonProcessingException {
        var accountShipping = accountShippingRepository.findAllById(shippingOrdersRequest.getAccountShippingId());

        String url = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);


        if (accountShipping.getAccountShippingType().getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_GHN) || accountShipping.getAccountShippingType().getCode().equals("GHNT")){
            return orderGHN(headers, accountShipping, shippingOrdersRequest, account, url);
        }

        if (accountShipping.getAccountShippingType().getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_GHSV)){
            return orderGHSV(headers, accountShipping, shippingOrdersRequest, account, url);
        }

        if (accountShipping.getAccountShippingType().getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_BEST)){
            return orderBEST(headers, accountShipping, shippingOrdersRequest, account);
        }
        return null;
    }

    public BaseResponse orderBEST(HttpHeaders headers, AccountShipping accountShipping, ShippingOrdersRequest shippingOrdersRequest, Account account){
        BaseResponse response = new BaseResponse();
        String url = accountShipping.getAccountShippingType().getUrl() + "/ADD";
        
        return null;
    }

    public BaseResponse orderGHSV(HttpHeaders headers, AccountShipping accountShipping,ShippingOrdersRequest shippingOrdersRequest, Account account, String url){
        BaseResponse response = new BaseResponse();
        url = accountShipping.getAccountShippingType().getUrl() + "/order/create";
        headers.set("Token", accountShipping.getToken());
        ArrayList<Data> dataCreateSuccess = new ArrayList<>();
        ArrayList<Data> dataCreateFail = new ArrayList<>();
        for (ShippingOrderItem item : shippingOrdersRequest.getShippingOrderItems()){
            try {
                String productName = "";
                int weight = 0;
                int totalHeight = 0;
                int totalLength = 0;
                int totalWith = 0;
                List<SubProduct> subProductList = new ArrayList<>();
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<ProductShippingOrder> productShippingOrderList = objectMapper.readValue(item.getData().getProductIds(), new TypeReference<List<ProductShippingOrder>>() {});
                for (ProductShippingOrder productShippingOrder : productShippingOrderList ){
                    SubProduct subProduct = subProductRepository.findAllById(productShippingOrder.getProduct().getId());
                    subProductList.add(subProduct);
                    if (productName.equals("")){
                        productName = productName + subProduct.getName() + "|" + subProduct.getCode() + "|" + subProduct.getProperties();
                    }else {
                        productName = productName + "-" + subProduct.getName() + "|" + subProduct.getCode() + "|" + subProduct.getProperties();
                    }
                    if (weight == 0 || weight >= subProduct.getWeight().intValue()){
                        weight = subProduct.getWeight().intValue();
                    }

                    if (totalHeight == 0 || totalHeight >= subProduct.getHigh().intValue()){
                        totalHeight = subProduct.getHigh().intValue();

                    }
                    if (totalLength == 0 || totalLength >= subProduct.getLength().intValue()){
                        totalLength = subProduct.getLength().intValue();
                    }

                    if (totalWith == 0 || totalWith >= subProduct.getWide().intValue()){
                        totalWith = subProduct.getWide().intValue();
                    }
                }
                String clientCode = "AXT." + System.currentTimeMillis();

                // build body api create order GHN
                ShippingOrderGHSVCreateBody body = ShippingOrderGHSVCreateBody.builder()
                        .shop_id(shippingOrdersRequest.getShopId())
                        .product(productName)
                        .client_code(clientCode)
                        .weight(weight)
                        .height(totalHeight)
                        .length(totalLength)
                        .width(totalWith)
                        .price(item.getData().getPrice().intValue())
                        .value(item.getData().getPrice().intValue())
                        .note(shippingOrdersRequest.getNoteShipping())
                        .config_collect(shippingOrdersRequest.getConfigCollect())
                        .config_delivery(OPTIONGHSV.OPTIONDELIVERYGHN.get(shippingOrdersRequest.getConfigDelivery()))
                        .to_name(item.getData().getName())
                        .to_phone(item.getData().getPhone())
                        .to_address(item.getTo_address())
                        .to_ward(item.getTo_ward())
                        .to_district(item.getTo_district())
                        .to_province(item.getTo_province())
                        .source("AXT")
                        .build();

                HttpEntity<ShippingOrderGHSVCreateBody> request = new HttpEntity<>(body, headers);
                ShippingOrderGHSVCreateReponse ghsvCreateReponse = restTemplate.postForObject(url, request, ShippingOrderGHSVCreateReponse.class);
                //Kiem tra xem da tao don thanh cong hay chua
                if (ghsvCreateReponse.getSuccess()){
                    item.getData().setStatus(8);
                    item.getData().setShippingCreator(account);
                    item.getData().setShippingCode(clientCode);
                    item.getData().setShippingAccount(accountShipping);
                    item.getData().setActualFee(ghsvCreateReponse.getOrder().getFee());
                    if(item.getData().getBoL() != null){
                        createExportBoL(item.getData());
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    Date tempDate = new Date();
                    LocalDateTime currentDate = tempDate.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
                    Long now = Long.parseLong(currentDate.format(formatter));
                    item.getData().setTimeShipping(now);
                    updateShippingCostStatisticsGeneral(item.getData());
                    item.getData().setCost(ghsvCreateReponse.getOrder().getFee().doubleValue());
                    dataRepository.save(item.getData());
                    Order order = Order.builder()
                            .product(ghsvCreateReponse.getOrder().getProduct())
                            .weight(ghsvCreateReponse.getOrder().getWeight())
                            .width(Double.valueOf(totalWith))
                            .height(Double.valueOf(totalHeight))
                            .length(Double.valueOf(totalLength))
                            .value(ghsvCreateReponse.getOrder().getValue())
                            .cod(ghsvCreateReponse.getOrder().getCod())
                            .shopId(String.valueOf(ghsvCreateReponse.getOrder().getShopId()))
                            .note(ghsvCreateReponse.getOrder().getNote())
                            .noteDelay(ghsvCreateReponse.getOrder().getNote())
                            .toName(ghsvCreateReponse.getOrder().getToName())
                            .toPhone(ghsvCreateReponse.getOrder().getToPhone())
                            .toAddress(ghsvCreateReponse.getOrder().getToAddress())
                            .toProvince(ghsvCreateReponse.getOrder().getToProvince())
                            .toDistrict(ghsvCreateReponse.getOrder().getToDistrict())
                            .toWard(ghsvCreateReponse.getOrder().getToWard())
                            .fee(ghsvCreateReponse.getOrder().getFee())
                            .clientCode(clientCode)
                            .requiredCode(ghsvCreateReponse.getOrder().getRequired_code())
                            .orderCode(clientCode)
                            .configCollect(String.valueOf(ghsvCreateReponse.getOrder().getConfigCollect()))
                            .dateCreate(now)
                            .build();
                    oReponsitory.save(order);
                    dataCreateSuccess.add(item.getData());
//                    createOrderWithGHN(item, ghnCreateReponse);
                    response.CODE=200;
                    response.MESSAGE = ghsvCreateReponse.getMsg();
                    response.RESULT = dataCreateSuccess;
                }else {
                    item.getData().setStatus(11);
                    dataCreateFail.add(item.getData());
                    response =  shippingOrderFail(ghsvCreateReponse.getMsg(), item);
                }

            }catch (Exception e){
                dataCreateFail.add(item.getData());
                item.getData().setStatus(11);
                dataCreateFail.add(item.getData());
                response =  shippingOrderFail("Lỗi không xác định!", item);
            }finally {
                return response;
            }
        }
        return response;
    }

    public BaseResponse shippingOrderFail(String msg, ShippingOrderItem item){
        ArrayList<Data> dataCreateFail = new ArrayList<>();
        item.getData().setStatus(11);
        item.getData().setNoteShipping(msg);
        dataRepository.save(item.getData());
        dataCreateFail.add(item.getData());
        return new BaseResponse(500, item.getData().getNoteShipping(), dataCreateFail);
    }

    //Tao don hang ben giao hang nhanh
    public BaseResponse orderGHN(HttpHeaders headers, AccountShipping accountShipping,ShippingOrdersRequest shippingOrdersRequest, Account account, String url) throws JsonProcessingException {
        url = accountShipping.getAccountShippingType().getUrl() +  "/shipping-order/create";
        BaseResponse response = new BaseResponse();
        headers.set("Token", accountShipping.getToken());
        headers.set("ShopId", String.valueOf(shippingOrdersRequest.getShopId()));
        ArrayList<Data> dataCreateSuccess = new ArrayList<>();
        ArrayList<Data> dataCreateFail = new ArrayList<>();
        for (ShippingOrderItem item : shippingOrdersRequest.getShippingOrderItems()){
            try {
                if (item.getData().getPhone().length() != 10){
                    response =  shippingOrderFail("Số điện thoại phải 10 kí tự", item);
                    return response;
                }

                if (item.getTo_address() == null || item.getTo_district() == null || item.getTo_province() == null || item.getTo_ward() == null){
                    response = shippingOrderFail("Thông tin địa chỉ người nhận không được để trống!", item);
                    return response;
                }
                int totalWeight = 0;
                int totalHeight = 0;
                int totalLength = 0;
                int totalWith = 0;
                List<Item> items = new ArrayList<>();
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<ProductShippingOrder> productShippingOrderList = objectMapper.readValue(item.getData().getProductIds(), new TypeReference<List<ProductShippingOrder>>() {});

                //Lay cac san pham duoc ban trong don hang
                for (ProductShippingOrder productShippingOrder : productShippingOrderList){
                    SubProduct subProduct = subProductRepository.findAllById(productShippingOrder.getProduct().getId());
                    if (totalHeight == 0 || totalHeight >= subProduct.getHigh().intValue()){
                        totalHeight = subProduct.getHigh().intValue();

                    }
                    if (totalLength == 0 || totalLength >= subProduct.getLength().intValue()){
                        totalLength = subProduct.getLength().intValue();
                    }

                    if (totalWeight == 0 || totalWeight >= subProduct.getWeight().intValue()){
                        totalWeight = subProduct.getWeight().intValue();
                    }

                    if (totalWith == 0 || totalWith >= subProduct.getWide().intValue()){
                        totalWith = subProduct.getWide().intValue();
                    }
                    Item product = Item.builder()
                            .name(subProduct.getName() + "|" + subProduct.getCode() + "|" + subProduct.getProperties())
                            .code(subProduct.getCode())
                            .price(subProduct.getPrice().intValue())
                            .width(subProduct.getWide().intValue())
                            .height(subProduct.getHigh().intValue())
                            .length(subProduct.getLength().intValue())
                            .quantity(productShippingOrder.getQuantity().intValue())
                            .build();
                    items.add(product);
                }

                int codeAmount = 0;
                if (shippingOrdersRequest.getConfigCollect() == 1){
                    codeAmount = item.getData().getPrice().intValue();
                }
                // build body api create order GHN
                ShippingOrderGHNCreateBody body = ShippingOrderGHNCreateBody.builder()
                        .payment_type_id(1)
                        .required_note(OPTIONGHN.OPTIONDELIVERYGHN.get(shippingOrdersRequest.getConfigDelivery()))
                        .client_order_code("")
                        .to_name(item.getData().getName())
                        .to_phone(item.getData().getPhone())
                        .to_address(item.getTo_address())
                        .to_ward_name(item.getTo_ward())
                        .to_district_name(item.getTo_district())
                        .to_province_name(item.getTo_province())
                        .cod_amount(codeAmount)
                        .weight(totalWeight)
                        .length(totalLength)
                        .height(totalHeight)
                        .width(totalWith)
                        .cod_failed_amount(0)
                        .pick_station_id(1)
                        .insurance_value(item.getData().getPrice().intValue())
                        .service_id(2)
                        .service_type_id(2)
                        .note(shippingOrdersRequest.getNoteShipping())
                        .items(items)
                        .build();

                HttpEntity<ShippingOrderGHNCreateBody> request = new HttpEntity<>(body, headers);
                ShippingOrderGHNCreateReponse ghnCreateReponse = restTemplate.postForObject(url, request, ShippingOrderGHNCreateReponse.class);
                //Kiem tra xem da tao don thanh cong hay chua
                if (ghnCreateReponse.getCode()==200){
                    item.getData().setStatus(8);
                    item.getData().setShippingCreator(account);
                    item.getData().setShippingCode(ghnCreateReponse.getData().getOrder_code());
                    item.getData().setShippingAccount(accountShipping);
                    createExportBoL(item.getData());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    Date tempDate = new Date();
                    LocalDateTime currentDate = tempDate.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
                    Long now = Long.parseLong(currentDate.format(formatter));
                    item.getData().setTimeShipping(now);
                    dataRepository.save(item.getData());
                    dataCreateSuccess.add(item.getData());
                    createOrderWithGHN(item, ghnCreateReponse);
                    response.CODE=200;
                    response.MESSAGE = ghnCreateReponse.getMessage();
                    response.RESULT = dataCreateSuccess;
                }else {
                    response =  shippingOrderFail("Lỗi không xác định!", item);
                }

            }catch (Exception e){
                response = shippingOrderFail("Lỗi không xác định!", item);
            }
        }
        return response;
    }

    public void createOrderWithGHN(ShippingOrderItem item, ShippingOrderGHNCreateReponse ghnCreateReponse){
        OrderShipping order = OrderShipping.builder()
                .orderCode(ghnCreateReponse.getData().getOrder_code())
                .clientCode(ghnCreateReponse.getData().getOrder_code())
                .build();
        orderRepository.save(order);
    }

    public void createExportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_CONFIRMED_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
//                .note(boL.getNote())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
        commingExportBoL(data);
    }

    public void commingExportBoL(Data data){
        BoL boL = data.getBoL();
        List<BoLDetail> boLDetail = boLDetailRepository.findAllByBoL(boL);
        BoL boLRequest = BoL.builder()
                .creator(boL.getCreator())
                .type(boL.getType())
                .status(BoLStatus.BOL_COMING_STATUS)
                .warehouse(boL.getWarehouse())
                .boLDetailList(boLDetail)
                .shop(boL.getShop())
//                .note(boL.getNote())
                .build();
        boLRequest.setId(boL.getId());
        CreateBoLRequest createBoLRequest = new CreateBoLRequest();
        createBoLRequest.setBoL(boLRequest);
        createBoLRequest.setBoLDetailList(boLDetail);
        boLService.create(createBoLRequest);
    }


    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void updateShippingCostStatisticsGeneral(Data data){
        List<Shop> shop = shopRepository.findAllByCode(data.getShopCode());
        List<StatisticsGeneral> statisticsGeneral = statisticsGeneralRepository.findAllByRangeDate(data.getDateOnly(), data.getDateOnly(), shop.get(0).getId());
        Long shippingCost = 0L;
        if (data.getCost() != null){
            shippingCost = data.getCost().longValue();
        }
        statisticsGeneral.get(0).setShippingCost(statisticsGeneral.get(0).getShippingCost() - shippingCost + data.getActualFee());
        statisticsGeneralRepository.save(statisticsGeneral.get(0));
    }
}
