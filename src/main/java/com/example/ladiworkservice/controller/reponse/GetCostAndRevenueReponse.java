package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetCostAndRevenueReponse {
    private Long costImport = 0L;
    private Long revenue = 0L;
}
