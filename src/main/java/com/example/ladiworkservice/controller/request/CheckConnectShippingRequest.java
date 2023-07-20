package com.example.ladiworkservice.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckConnectShippingRequest {
    @JsonProperty(value = "typeId")
    private Long typeId;

    @JsonProperty(value = "token")
    private String token;
}
