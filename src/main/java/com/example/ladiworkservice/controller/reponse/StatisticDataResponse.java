package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDataResponse {
    private Long sales;
    private Long netSales;
    private Long costDeliveryFee;
    private Long costReturnGood;
    private Long costNetDeliveryFee;
    private Long revenue;
    private Long netRevenue;
}
