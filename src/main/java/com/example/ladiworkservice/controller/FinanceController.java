package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Finance;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/finance")
@CrossOrigin
public class FinanceController extends BaseController<Finance> {
    @Autowired
    FinanceService financeService;

    @Override
    protected BaseService<Finance> getService() {
        return financeService;
    }

    @PostMapping("/session")
    BaseResponse session(@RequestParam MultipartFile file
            , @RequestParam Long accountShippingTypeId) throws Exception {
        financeService.session(accountShippingTypeId, file);
        return new BaseResponse(200, "Đối soát thành công!", null);
    }
}
