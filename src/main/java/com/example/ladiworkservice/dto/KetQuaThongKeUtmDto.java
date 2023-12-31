package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaThongKeUtmDto {
    private Long date;
    private String utmName;
    private int count;
}
