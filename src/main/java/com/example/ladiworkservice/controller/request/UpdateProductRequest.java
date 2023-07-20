package com.example.ladiworkservice.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {
    private Long id;
    private String code;
    private String name;
    private Long warehouseId;
    private Long categoryId;
    private String image;
    private String note;
    private String properties;
    private int status;
    private List<CreateSubProductRequest> subProductList;
}
