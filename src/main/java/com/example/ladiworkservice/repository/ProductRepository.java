package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.controller.reponse.StatisticProductReponse;
import com.example.ladiworkservice.model.Product;
import com.example.ladiworkservice.model.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
    List<Product> findAllByStatus(int status);
    List<Product> findAllByShopcodeAndStatus(String shopcode, int status);
    List<Product> findAllByCodeAndShop(String code, Shop shop);
//    package com.example.ladi.controller.reponse;
    @Query("SELECT new com.example.ladiworkservice.controller.reponse.StatisticProductReponse(SUM(sp.totalQuantity), SUM(sp.availableQuantity), SUM(sp.awaitingProductQuantity), SUM(sp.defectiveProductQuantity), SUM(sp.inventoryQuantity), SUM(sp.inventoryQuantity * sp.lastImportedPrice)) FROM SubProduct sp, Product p where sp.product.id = p.id and p.shop.id = :shopId")
    StatisticProductReponse statisticProduct(Long shopId);
}
