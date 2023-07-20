package com.example.ladiworkservice.service.httpService;

import org.springframework.http.HttpEntity;

public interface HttpService<T>{
    T post(Object body);
    T put(Object body);
}
