package com.example.ladiworkservice.shippingorders.ghn.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckConnectGHNResponse {

    @JsonProperty(value = "code")
    private int code;
}
