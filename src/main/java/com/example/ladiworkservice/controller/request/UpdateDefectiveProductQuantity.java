package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDefectiveProductQuantity {
    private Long warehouseId;
    private Long ProductsId;
    private int status;
    private Long quantity;
}
