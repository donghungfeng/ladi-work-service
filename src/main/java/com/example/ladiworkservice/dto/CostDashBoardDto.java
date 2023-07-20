package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDashBoardDto extends CostDataDashBoardDto {
    private Long totalCostPrice;
    private Long otherCost;
    private Double totalCostPricePercent;
    private Long totalCostPriceAfterRefund;
    private Double totalCostMarketingPercent;
    private Double totalShipCost;
    private Long totalDeliveryFeeAfterRefund;
    private Long totalCostAfterRefund;
    private Double totalDeliveryAfterRefundPercent;
    private Double totalDeliveryPercent;
    private Double totalCostAfterRefundPercent;
}
