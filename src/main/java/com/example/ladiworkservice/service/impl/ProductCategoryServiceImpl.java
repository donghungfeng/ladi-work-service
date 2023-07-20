package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.ProductCategory;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.ProductCategoryRepository;
import com.example.ladiworkservice.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory> implements ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Override
    protected BaseRepository<ProductCategory> getRepository() {
        return productCategoryRepository;
    }

    @Override
    public BaseResponse creates(List<ProductCategory> productCategoryList) {

        return new BaseResponse(200, "Ok", productCategoryRepository.saveAll(productCategoryList));
    }
}
