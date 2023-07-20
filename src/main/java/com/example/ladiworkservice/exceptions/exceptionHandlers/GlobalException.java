package com.example.ladiworkservice.exceptions.exceptionHandlers;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.exceptions.FindByIdNullException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> RuntimeExceptionHandler(RuntimeException exception) {
        BaseResponse response = new BaseResponse();
        response.MESSAGE = exception.getMessage();
        response.CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({JsonProcessingException.class, JsonMappingException.class})
    public ResponseEntity<BaseResponse> JsonMappingExceptionHandler(RuntimeException exception) {
        BaseResponse response = new BaseResponse();
        response.MESSAGE = exception.getMessage();
        response.CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({FindByIdNullException.class})
    public ResponseEntity<BaseResponse> FindByIdNullExceptionHandler(FindByIdNullException exception) {
        BaseResponse response = new BaseResponse();
        response.MESSAGE = exception.getMessage();
        response.CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
