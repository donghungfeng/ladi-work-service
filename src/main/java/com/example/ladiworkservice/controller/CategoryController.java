package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.Category;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@CrossOrigin
@PreAuthorize("hasAuthority('admin')")
public class CategoryController extends BaseController<Category>{
    @Autowired
    CategoryService categoryService;
    @Override
    protected BaseService<Category> getService() {
        return categoryService;
    }
}
