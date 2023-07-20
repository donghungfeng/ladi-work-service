package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.request.CheckConnectShippingRequest;
import com.example.ladiworkservice.model.AccountShipping;
import com.example.ladiworkservice.service.AccountShippingService;
import com.example.ladiworkservice.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account_shipping")
@CrossOrigin
public class AccountShippingController extends BaseController<AccountShipping>{
    @Autowired
    AccountShippingService accountShippingService;
    @Override
    protected BaseService<AccountShipping> getService() {
        return accountShippingService;
    }
    @GetMapping("get_shop_by_token")
    public ResponseEntity findShopByToken(@RequestParam Long id){
        return ResponseEntity.ok(accountShippingService.findShopByToken(id));
    }

    @PostMapping("checkConnect")
    public ResponseEntity checkConnect(@RequestBody CheckConnectShippingRequest checkConnectShippingRequest){
        return ResponseEntity.ok(accountShippingService.checkConnect(checkConnectShippingRequest.getTypeId(), checkConnectShippingRequest.getToken()));
    }
}
