package com.example.ladiworkservice.controller.reponse;

import org.springframework.stereotype.Component;

@Component
public class CreateListOrderGHSVResponse extends BaseResponse {
    public Integer SuccessOrderTotal;
    public Integer FailOrderTotal;

    public CreateListOrderGHSVResponse() {}

    public CreateListOrderGHSVResponse(int code, String message, Object result, Integer successOrderTotal, Integer failOrderTotal) {
        super(code, message, result);
        SuccessOrderTotal = successOrderTotal;
        FailOrderTotal = failOrderTotal;
    }
}
