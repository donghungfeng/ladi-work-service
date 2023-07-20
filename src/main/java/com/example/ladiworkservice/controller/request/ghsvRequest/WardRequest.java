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
public class WardRequest {
    @JsonProperty("district_code")
    public Long district_code;
}
