package com.example.ladiworkservice.controller.reponse;

import org.springframework.stereotype.Component;
@Component
//@Getter
//@Setter
public class BaseResponse {
    public int CODE;
    public String MESSAGE;
    public Object RESULT;

    public BaseResponse() {}

    public BaseResponse(int code, String message, Object result) {
        this.CODE = code;
        this.MESSAGE = message;
        this.RESULT = result;
    }

    public BaseResponse success(Object RESULT){
        return new BaseResponse(200, "Ok", RESULT);
    }
    public BaseResponse fail(Object RESULT){return new BaseResponse(500, "Fail", RESULT);}
}
