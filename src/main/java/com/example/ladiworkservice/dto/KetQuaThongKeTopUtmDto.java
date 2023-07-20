package com.example.ladiworkservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KetQuaThongKeTopUtmDto {
    private String userName;
    private String fullName;
    private Long count;
}
