package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.ProductCategory;

public interface ProductCategoryRepository extends BaseRepository<ProductCategory>{
    ProductCategory findAllByProductId(Long productId);
}
