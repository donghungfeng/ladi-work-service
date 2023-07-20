package com.example.ladiworkservice.controller.request.ghsvRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictRequest {
    @JsonProperty("province_code")
    public Long province_code;
}
