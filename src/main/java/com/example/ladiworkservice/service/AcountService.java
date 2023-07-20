package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Account;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AcountService extends BaseService<Account> {
    public BaseResponse login(String userName, String password) throws NoSuchAlgorithmException;

    List<Account> getMtkByShop(String shopcode);
}
