package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService extends BaseService<ProductCategory> {
    public BaseResponse creates(List<ProductCategory> productCategoryList);
}
