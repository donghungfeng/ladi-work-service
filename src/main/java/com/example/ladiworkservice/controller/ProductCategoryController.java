package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.ProductCategory;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product_category")
@CrossOrigin
@PreAuthorize("hasAuthority('admin')")
public class ProductCategoryController extends BaseController<ProductCategory>{
    @Autowired
    ProductCategoryService categoryService;
    @Override
    protected BaseService<ProductCategory> getService() {
        return categoryService;
    }

    @PostMapping("creates")
    public BaseResponse creates(@RequestBody List<ProductCategory> productCategoryList){
        return categoryService.creates(productCategoryList);
    }
}
