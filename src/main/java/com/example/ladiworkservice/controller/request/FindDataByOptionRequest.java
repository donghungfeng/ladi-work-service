package com.example.ladiworkservice.controller.request;

import com.example.ladiworkservice.dto.DateRangeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindDataByOptionRequest {
    public String Status;
    public DateRangeDto date;
}




