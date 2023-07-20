package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticsRevenueResponse {
    private Long costPrice;
    private Double shippingCost;
    private Double revenue;
    private Long totalOrder;
}
