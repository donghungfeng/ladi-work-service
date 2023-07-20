package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticBoLRespone {
    private Long totalInventoryQuantity;
    private Long totalProduct;
    private Long totalPrice;
}
