package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckOutRequest {
    private Long id;
    private Long timeOut;
    private int totalOrder;
    private int successOrder;
    private int processedOrder;
    private int onlyOrder;
    private int processedOnlyOrder;
}
