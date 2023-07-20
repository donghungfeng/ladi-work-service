package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.AccountShippingGroup;
import com.example.ladiworkservice.service.AccountShippingGroupService;
import com.example.ladiworkservice.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account_shipping_group")
@CrossOrigin
public class AccountShippingGroupController extends BaseController<AccountShippingGroup>{
    @Autowired
    AccountShippingGroupService accountShippingGroupService;

    @Override
    protected BaseService<AccountShippingGroup> getService() {
        return accountShippingGroupService;
    }
}
