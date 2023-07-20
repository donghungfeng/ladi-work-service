package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateProductRequest;
import com.example.ladiworkservice.controller.request.UpdateSoldProductRequest;

import java.util.List;

public interface ProductService{
    BaseResponse getAllByShopcodeAndStatus(String shopcode, int status);
    BaseResponse create(CreateProductRequest createProductRequest);
    BaseResponse search(String filter, String sort, int size, int page);
    BaseResponse updateSoldProduct(List<UpdateSoldProductRequest> ids);
    BaseResponse statisticProduct(Long shopId);
//    BaseResponse update(UpdateProductRequest updateProductRequest);
}
