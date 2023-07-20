package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateAccountShippingTypeRequest;
import com.example.ladiworkservice.dto.AccountShippingDto;
import com.example.ladiworkservice.model.AccountShippingType;
import com.example.ladiworkservice.service.AccountShippingTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("accountShipping_type")
@CrossOrigin
public class AccountShippingTypeController {
    Logger logger = LoggerFactory.getLogger(AccountShippingTypeController.class);

    private final AccountShippingTypeService accountShippingTypeService;

    public AccountShippingTypeController(AccountShippingTypeService accountShippingTypeService) {
        this.accountShippingTypeService = accountShippingTypeService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody @Valid CreateAccountShippingTypeRequest request) {
        BaseResponse response;
        AccountShippingType type = new AccountShippingType();
        type.setName(request.getName());
        type.setNote(request.getNote());

        try {
            response = accountShippingTypeService.create(type);
        }
        catch (RuntimeException ex) {
            logger.error(String.format("AccountShippingTypeController|create|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        BaseResponse response;

        try {
            response = accountShippingTypeService.getAll();
        }
        catch (Exception ex) {
            logger.error(String.format("AccountShippingTypeController|getAll|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable(name = "id") Long id) {
        BaseResponse response;

        try {
            response = accountShippingTypeService.getById(id);
        }
        catch (Exception ex) {
            logger.error(String.format("AccountShippingTypeController|getById|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> deleteById(@PathVariable(name = "id") Long id) {
        BaseResponse response;

        try {
            response = accountShippingTypeService.deleteById(id);
        }
        catch (Exception ex) {
            logger.error(String.format("AccountShippingTypeController|deleteById|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable(name = "id") Long id, @RequestBody AccountShippingDto typeDto) {
        BaseResponse response;

        AccountShippingType type = new AccountShippingType();
        type.setName(typeDto.getName());
        type.setNote(typeDto.getToken());

        try {
            response = accountShippingTypeService.update(id, type);
        }
        catch (Exception ex) {
            logger.error(String.format("Type|update|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
