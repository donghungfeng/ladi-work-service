package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticPerformanceSaleDto {
    private String fullName;
    private int totalOrder;
    private int successOrder;
    private int processedOrder;
    private int onlyOrder;
    private int processedOnlyOrder;
    private String percentSuccessOrder;
    private String percentProcessedOrder;
    private String percentOnlyOrder;
    private String percentProcessedOnlyOrder;

    public StatisticPerformanceSaleDto(String fullName, int totalOrder, int successOrder, int processedOrder, int onlyOrder, int processedOnlyOrder) {
        this.fullName = fullName;
        this.totalOrder = totalOrder;
        this.successOrder = successOrder;
        this.processedOrder = processedOrder;
        this.onlyOrder = onlyOrder;
        this.processedOnlyOrder = processedOnlyOrder;
        this.percentOnlyOrder = "100";
        this.percentProcessedOnlyOrder = "100";
        this.percentProcessedOrder = "100";
        this.percentSuccessOrder = "100";
    }
}
