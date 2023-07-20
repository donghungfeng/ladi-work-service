package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateSubProductRequest {
    private Long id;
    private int status;
    private String image;
    private Long lastImportedPrice = 0L;
    private String code;
    private Long price = 0L;
    private Double size = 0.0;
    private Double length = 0.0;
    private Double wide = 0.0;
    private Double height = 0.0;
    private Long sold = 0L;
    private String properties;
    private Long totalQuantity = 0L;
    private Long availableQuantity = 0L;
    private Long defectiveProductQuantity = 0L;
    private Long inventoryQuantity = 0L;
    private Long awaitingProductQuantity = 0L;
}
