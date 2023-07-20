package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.exceptions.FindByIdNullException;
import com.example.ladiworkservice.model.AccountShippingType;
import com.example.ladiworkservice.repository.AccountShippingTypeRepository;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.service.AccountShippingTypeService;
import org.springframework.stereotype.Service;

@Service
public class AccountShippingTypeServiceImpl extends BaseServiceImpl<AccountShippingType> implements AccountShippingTypeService {
    private AccountShippingTypeRepository accountShippingTypeRepository;

    public AccountShippingTypeServiceImpl(AccountShippingTypeRepository accountShippingTypeRepository) {
        this.accountShippingTypeRepository = accountShippingTypeRepository;
    }

    @Override
    protected BaseRepository<AccountShippingType> getRepository() {
        return accountShippingTypeRepository;
    }

    @Override
    public AccountShippingType findById(Long id) {
        return accountShippingTypeRepository.findById(id).orElseThrow(
                () -> new FindByIdNullException(String.format("Không tồn tại bản ghi với id: %s", id))
        );
    }
}
