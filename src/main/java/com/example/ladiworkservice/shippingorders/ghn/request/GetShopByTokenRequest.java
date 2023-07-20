package com.example.ladiworkservice.shippingorders.ghn.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetShopByTokenRequest {
    @JsonProperty(value = "limit")
    private int limit;

    @JsonProperty(value = "ofset")
    private int offset;

    @JsonProperty(value = "client_phone")
    private String clientPhone;

}
