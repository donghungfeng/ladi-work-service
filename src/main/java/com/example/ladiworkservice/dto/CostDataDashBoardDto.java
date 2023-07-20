package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDataDashBoardDto {
    private Long totalMarketingCost;
    private Long totalOperationCost;
    private Long totalCost;
    private Long otherCost;
}
