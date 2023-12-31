package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticUtmByDateDto {
    private int count;
    private Long date;
    private String utmMedium;
    private Long price;
}
