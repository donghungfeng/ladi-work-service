package com.example.ladiworkservice.shippingorders.ghn.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetShopByTokenShopGHN {
    @JsonProperty(value = "_id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;
}
