package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticCostByStatusData {
    public Long total;
    public Long cogs;
    public Long date;
    public Long totalProductValue;
}
