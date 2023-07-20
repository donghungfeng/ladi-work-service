package com.example.ladiworkservice.shippingorders.ghn.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrackingBodyGHSVRequest {
    @JsonProperty(value = "client_code")
    private String clientCode;
}
