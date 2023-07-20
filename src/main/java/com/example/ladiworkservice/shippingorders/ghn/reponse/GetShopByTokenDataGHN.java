package com.example.ladiworkservice.shippingorders.ghn.reponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetShopByTokenDataGHN {
    @JsonProperty(value = "last_offset")
    private Long lastOffset;

    @JsonProperty(value = "shops")
    private List<GetShopByTokenShopGHN> shopsList;
}
