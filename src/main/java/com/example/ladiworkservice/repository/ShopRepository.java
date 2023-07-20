package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends BaseRepository<Shop> {
    List<Shop> findAllByStatus(int status);

    List<Shop> findAllByCode(String code);

    List<Shop> findAllByStatusAndCode(int status, String Code);

    Shop findShopByCode(String Code);

    @Query("SELECT s FROM Shop s WHERE LOWER(s.name) = LOWER(:name) ")
    Optional<Shop> findByName(String name);
}
