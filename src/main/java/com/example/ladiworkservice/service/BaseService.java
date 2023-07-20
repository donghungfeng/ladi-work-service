package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.NoSuchAlgorithmException;


public interface BaseService<T> {
    public BaseResponse search(String filter, String sort, int size, int page);
    public BaseResponse getAll();
    public BaseResponse create(T t) throws JsonProcessingException;
    public BaseResponse update(Long id, T t) throws NoSuchAlgorithmException, JsonProcessingException;
    public BaseResponse getById(Long id);
    public BaseResponse deleteById(Long id) throws JsonProcessingException;
}
