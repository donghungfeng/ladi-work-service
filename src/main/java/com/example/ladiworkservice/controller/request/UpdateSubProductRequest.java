package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateSubProductRequest {
    private Long id;
    private int status;
    private String image;
    private String code;
    private Long lastImportedPrice;
    private Long price;
    private String properties;
    private Double size;
    private Double weight;
    private Long totalQuantity = 0L;
    private Long availableQuantity = 0L;
    private Long defectiveProductQuantity = 0L;
    private Long inventoryQuantity = 0L;
    private Long awaitingProductQuantity = 0L;
}
