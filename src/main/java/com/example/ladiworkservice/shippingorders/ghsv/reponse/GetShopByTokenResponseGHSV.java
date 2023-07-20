package com.example.ladiworkservice.shippingorders.ghsv.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetShopByTokenResponseGHSV {
    @JsonProperty(value = "shops")
    private List<GetShopByTokenShopGHSV> shopList;
}
