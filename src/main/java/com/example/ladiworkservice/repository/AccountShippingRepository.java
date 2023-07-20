package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.AccountShipping;

import java.util.List;

public interface AccountShippingRepository  extends BaseRepository<AccountShipping>{
    AccountShipping findByShopId(Long shipId);
    List<AccountShipping> findAllByToken(String token);

    List<AccountShipping> findAllByIsDefault(int isDefault);
}
