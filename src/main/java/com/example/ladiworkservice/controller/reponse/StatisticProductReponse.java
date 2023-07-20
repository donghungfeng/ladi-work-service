package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticProductReponse {
    private Long totalImportProduct = 0L;
    private Long totalAvailableProduct = 0L;
    private Long totalAwaitingProduct = 0L;
    private Long totalDefectiveProduct = 0L;
    private Long totalInventoryQuantity = 0L;
    private Long price = 0L;
}
