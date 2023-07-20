package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.AccountShippingGroup;
import com.example.ladiworkservice.repository.AccountShippingGroupRepository;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.service.AccountShippingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountShippingGroupServiceImpl extends BaseServiceImpl<AccountShippingGroup> implements AccountShippingGroupService {

    @Autowired
    AccountShippingGroupRepository accountShippingGroupRepository;

    @Override
    protected BaseRepository<AccountShippingGroup> getRepository() {
        return accountShippingGroupRepository;
    }

    @Override
    public BaseResponse create(AccountShippingGroup accountShippingGroup) {
        if (accountShippingGroup.getShop() == null){
            return new BaseResponse().fail("Shop không được để trống!");
        }
//        if (!accountShippingGroupRepository.findAllByCode(accountShippingGroup.getCode()).isEmpty()){
//            return new BaseResponse().fail("Code đã tồn tại!");
//        }
        if(accountShippingGroup.getIsDefault() == 1){
            List<AccountShippingGroup> accountShippingGroupList = accountShippingGroupRepository.findAllByIsDefault(1);
            for (AccountShippingGroup item : accountShippingGroupList){
                item.setIsDefault(0);
            }
            accountShippingGroupRepository.saveAll(accountShippingGroupList);
        }
        return new BaseResponse().success(accountShippingGroupRepository.save(accountShippingGroup));
    }

    @Override
    public BaseResponse update(Long id,AccountShippingGroup accountShippingGroup)  {
        if(accountShippingGroupRepository.findAllById(id) == null){
            return new BaseResponse().fail("Không tồn tại bản ghi với id: " + id);
        }
        if (accountShippingGroup.getShop() == null){
            return new BaseResponse().fail("Shop không được để trống!");
        }
//        if (!accountShippingGroupRepository.findAllByCode(accountShippingGroup.getCode()).isEmpty()){
//            return new BaseResponse().fail("Code đã tồn tại!");
//        }
        if(accountShippingGroup.getIsDefault() == 1){
            List<AccountShippingGroup> accountShippingGroupList = accountShippingGroupRepository.findAllByIsDefault(1);
            for (AccountShippingGroup item : accountShippingGroupList){
                item.setIsDefault(0);
            }
            accountShippingGroupRepository.saveAll(accountShippingGroupList);
        }
        return new BaseResponse().success(accountShippingGroupRepository.save(accountShippingGroup));   
    }
}
