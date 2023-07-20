package com.example.ladiworkservice.shippingorders.ghn.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetShopByTokenReponseGHN {
    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "data")
    private GetShopByTokenDataGHN data;


}
