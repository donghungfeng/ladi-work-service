package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.model.Category;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.CategoryRepository;
import com.example.ladiworkservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    protected BaseRepository<Category> getRepository() {
        return categoryRepository;
    }
}
