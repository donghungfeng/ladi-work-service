package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.global_variable.SHIPPINGTYPE;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.AccountShipping;
import com.example.ladiworkservice.model.AccountShippingType;
import com.example.ladiworkservice.repository.AccountShippingRepository;
import com.example.ladiworkservice.repository.AccountShippingTypeRepository;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.service.AccountShippingService;
import com.example.ladiworkservice.shippingorders.ghn.reponse.CheckConnectGHNResponse;
import com.example.ladiworkservice.shippingorders.ghn.reponse.GetShopByTokenReponseGHN;
import com.example.ladiworkservice.shippingorders.ghn.reponse.GetShopByTokenResult;
import com.example.ladiworkservice.shippingorders.ghn.reponse.GetShopByTokenShopGHN;
import com.example.ladiworkservice.shippingorders.ghn.request.GetShopByTokenRequest;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.CheckConnectGHSVResponse;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.GetShopByTokenResponseGHSV;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.GetShopByTokenShopGHSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountShippingServiceImpl extends BaseServiceImpl<AccountShipping> implements AccountShippingService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountShippingRepository accountShippingRepository;
  
    @Autowired
    AccountShippingTypeRepository accountShippingTypeRepository;

    @Override
    protected BaseRepository<AccountShipping> getRepository() {
        return accountShippingRepository;
    }

    @Override
    public BaseResponse create(AccountShipping accountShipping) {
        if (accountShipping.getAccountShippingType() == null){
            return new BaseResponse().fail("Đơn vị vận chuyển không được để trống!");
        }
//        if (!accountShippingGroupRepository.findAllByCode(accountShippingGroup.getCode()).isEmpty()){
//            return new BaseResponse().fail("Code đã tồn tại!");
//        }
        if(accountShipping.getIsDefault() == 1){
            List<AccountShipping> accountShippingList = accountShippingRepository.findAllByIsDefault(1);
            for (AccountShipping item : accountShippingList){
                item.setIsDefault(0);
            }
            accountShippingRepository.saveAll(accountShippingList);
        }
        return new BaseResponse().success(accountShippingRepository.save(accountShipping));
    }
    @Override
    public BaseResponse update(Long id,AccountShipping accountShipping)  {
        if(accountShippingRepository.findAllById(id) == null){
            return new BaseResponse().fail("Không tồn tại bản ghi với id: " + id);
        }
        if (accountShipping.getAccountShippingType() == null){
            return new BaseResponse().fail("Đơn vị vận chuyển không được để trống!");
        }
        if(accountShipping.getIsDefault() == 1){
            List<AccountShipping> accountShippingList = accountShippingRepository.findAllByIsDefault(1);
            for (AccountShipping item : accountShippingList){
                item.setIsDefault(0);
            }
            accountShippingRepository.saveAll(accountShippingList);
        }
        return new BaseResponse().success(accountShippingRepository.save(accountShipping));
    }

    @Override
    public BaseResponse findShopByToken(Long id){
        AccountShipping accountShipping = accountShippingRepository.findAllById(id);
        if (accountShipping == null){
            return new BaseResponse().fail("Tài khoản vận chuyển không tồn tại!");
        }
        if(accountShipping.getAccountShippingType().getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_GHN) || accountShipping.getAccountShippingType().getCode().equals("GHNT")){
            return findShopByTokenGHN(accountShipping);
        }
        if(accountShipping.getAccountShippingType().getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_GHSV)){
            return findShopByTokenGHSV(accountShipping);
        }
        return null;
    }

    @Override
    public BaseResponse checkConnect(Long shippingType, String token) {
        AccountShippingType accountShippingType = accountShippingTypeRepository.findAllById(shippingType);
        if (accountShippingType == null){
            return new BaseResponse().fail("Không tồn tại bản ghi với id: " + shippingType);
        }

        if (accountShippingType.getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_GHN) || accountShippingType.getCode().equals("GHNT")){
            return checkConnectGHN(token, accountShippingType);
        }

        if (accountShippingType.getCode().equals(SHIPPINGTYPE.SHIPPINGTYPE_GHSV)){
            return checkConnectGHSV(token, accountShippingType);
        }
        return null;
    }

    public BaseResponse checkConnectGHN(String token, AccountShippingType accountShippingType){
        String url = accountShippingType.getUrl() + "/shop/all";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        GetShopByTokenRequest body = GetShopByTokenRequest.builder()
                .limit(200)
                .build();
        HttpEntity<GetShopByTokenRequest> request = new HttpEntity<>(body, headers);
        CheckConnectGHNResponse response = restTemplate.postForObject(url, request, CheckConnectGHNResponse.class);
        if (response.getCode() == 200){
            return new BaseResponse().success("Kết nối thành công!");
        }
        return new BaseResponse().fail("Kết nối thất bại!");
    }

    public BaseResponse checkConnectGHSV(String token, AccountShippingType accountShippingType){
        String url = accountShippingType.getUrl() + "/shop/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        CheckConnectGHSVResponse response = restTemplate.exchange(url, HttpMethod.GET, request, CheckConnectGHSVResponse.class).getBody();
        if (response.getSuccess() == true){
            return new BaseResponse().success("Kết nối thành công!");
        }
        return new BaseResponse().fail("Kết nối thất bại!");
    }


    public BaseResponse findShopByTokenGHN(AccountShipping accountShipping){
        String url = accountShipping.getAccountShippingType().getUrl() + "/shop/all";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", accountShipping.getToken());
        GetShopByTokenRequest body = GetShopByTokenRequest.builder()
                .limit(200)
                .build();
        HttpEntity<GetShopByTokenRequest> request = new HttpEntity<>(body, headers);
        GetShopByTokenReponseGHN response = restTemplate.postForObject(url, request, GetShopByTokenReponseGHN.class);
        List<GetShopByTokenResult> results = new ArrayList<>();
        for (GetShopByTokenShopGHN item : response.getData().getShopsList()){
            GetShopByTokenResult result = new GetShopByTokenResult(item.getId(), item.getName());
            results.add(result);
        }
        return new BaseResponse().success(results);
    }

    public BaseResponse findShopByTokenGHSV(AccountShipping accountShipping){
        String url = accountShipping.getAccountShippingType().getUrl() + "/shop/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", accountShipping.getToken());
        List<GetShopByTokenResult> results = new ArrayList<>();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        GetShopByTokenResponseGHSV response = restTemplate.exchange(url, HttpMethod.GET, request, GetShopByTokenResponseGHSV.class).getBody();
        for (GetShopByTokenShopGHSV item :response.getShopList()){
            GetShopByTokenResult result = new GetShopByTokenResult(item.getId(), item.getName());
            results.add(result);
        }
        return new BaseResponse().success(results);
    }

}
