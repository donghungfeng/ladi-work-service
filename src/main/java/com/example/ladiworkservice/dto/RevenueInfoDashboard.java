package com.example.ladiworkservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueInfoDashboard {
    private Double refundOrderRate;
    private Long revenue;
    private Double revenueAfterRefund;
    private Long sales;
    private Double averagePrice;
    private Double orderAcceptRate;
    @JsonIgnore
    private Long totalCostPriceAfterRefund;
    @JsonIgnore
    private Long totalCostPrice;
    @JsonIgnore
    private Double totalDeliveryFee;
    @JsonIgnore
    private Integer totalDeliveryFeeAfterRefund;
    @JsonIgnore
    private Integer totalOrder;
    private Integer totalCustomer;
}
