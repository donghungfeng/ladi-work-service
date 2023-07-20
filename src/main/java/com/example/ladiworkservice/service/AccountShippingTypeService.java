package com.example.ladiworkservice.service;

import com.example.ladiworkservice.model.AccountShippingType;

public interface AccountShippingTypeService extends BaseService<AccountShippingType> {

    AccountShippingType findById(Long id);
}
