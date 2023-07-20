package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.CallInfo;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.CallInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("call")
@CrossOrigin
public class CallInfoController extends BaseController<CallInfo>{
    @Autowired
    CallInfoService callInfoService;
    @Override
    protected BaseService<CallInfo> getService() {
        return callInfoService;
    }

    @GetMapping("get-active")
    public BaseResponse getActive(){
        return callInfoService.getCallActive();
    }

    @GetMapping("active")
    public BaseResponse active(@RequestParam Long id){
        return callInfoService.active(id);
    }

    @GetMapping("deactive")
    public BaseResponse deActive(@RequestParam Long id){
        return callInfoService.deActive(id);
    }

    @GetMapping("heath-check")
    public BaseResponse heathCheck(){
        return callInfoService.heathCheck();
    }
}
