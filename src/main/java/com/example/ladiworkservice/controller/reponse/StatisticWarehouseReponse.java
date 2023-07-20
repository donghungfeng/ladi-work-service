package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticWarehouseReponse {
    private Long totalImportProduct = 0L;
    private Long totalInventoryQuantity = 0L;
    private Long totalAwaitingProduct = 0L;
    private Long price = 0L;
}
