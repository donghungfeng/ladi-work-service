package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.service.BaseService;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public abstract class BaseController<T> {
    protected abstract BaseService<T> getService();

    @GetMapping("getAll")
    public BaseResponse getAll(){
        return this.getService().getAll();
    }

    @PostMapping("create")
    public BaseResponse create(@RequestBody T t) throws NoSuchAlgorithmException, IOException {
        return this.getService().create(t);
    }

    @PutMapping("update")
    public BaseResponse update(@RequestParam Long id,@RequestBody T t) throws NoSuchAlgorithmException, JsonProcessingException {
        return this.getService().update(id, t);
    }

    @GetMapping("details")
    public BaseResponse details(@RequestParam(name = "id") Long id){
        return this.getService().getById(id);
    }

    @DeleteMapping("delete")
    public BaseResponse delete(@RequestParam(name = "id") Long id) throws JsonProcessingException {
        return this.getService().deleteById(id);
    }

    @GetMapping("search")
    public BaseResponse search(@RequestParam String filter, @RequestParam String sort, @RequestParam int size, @RequestParam int page){
        return this.getService().search(filter, sort, size, page);
    }
}
