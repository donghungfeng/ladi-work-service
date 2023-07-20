package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.AccountShippingGroup;

import java.util.List;

public interface AccountShippingGroupRepository extends BaseRepository<AccountShippingGroup>{
//    List<AccountShippingGroup> findAllByCode(String code);
    List<AccountShippingGroup> findAllByIsDefault(int isDefault);
}
