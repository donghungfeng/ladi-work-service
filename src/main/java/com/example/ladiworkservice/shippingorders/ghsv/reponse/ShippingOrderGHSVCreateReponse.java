package com.example.ladiworkservice.shippingorders.ghsv.reponse;


import com.example.ladiworkservice.shippingorders.ghsv.request.DataGHSV;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShippingOrderGHSVCreateReponse {
    @JsonProperty(value = "success")
    private Boolean success;
    @JsonProperty(value = "msg")
    private String msg;
    @JsonProperty("order")
    private DataGHSV order;
}
