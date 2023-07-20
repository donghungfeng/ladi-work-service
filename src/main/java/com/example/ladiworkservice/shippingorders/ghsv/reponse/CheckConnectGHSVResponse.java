package com.example.ladiworkservice.shippingorders.ghsv.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckConnectGHSVResponse {
    @JsonProperty(value = "success")
    private Boolean success;
}
