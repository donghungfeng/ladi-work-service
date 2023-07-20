package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Product;
import com.example.ladiworkservice.model.SubProduct;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubProductRepository extends BaseRepository<SubProduct>{
    List<SubProduct> findAllByCodeAndProduct(String code, Product product);
    List<SubProduct> findAllByProduct(Product product);
    List<SubProduct> findAllByPropertiesAndProduct(String properties, Product product);
    @Query(value = "SELECT * from sub_product where id in ids", nativeQuery = true)
    List<SubProduct> findAllByInId (List<Long> ids );

}
