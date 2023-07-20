package com.example.ladiworkservice.shippingorders.ghsv.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataGHSV {
    @JsonProperty(value = "client_code")
    private String client_code;
    @JsonProperty(value = "required_code")
    private String required_code;
    @JsonProperty(value = "sorting_code")
    private String sorting_code;
    @JsonProperty(value = "product")
    private String product;
    @JsonProperty(value = "weight")
    private Double weight;
    @JsonProperty(value = "value")
    private Long value;
    @JsonProperty(value = "cod")
    private Long cod;
    @JsonProperty(value = "note")
    private String note;
    @JsonProperty(value = "config_collect")
    private int configCollect;
    @JsonProperty(value = "shop_id")
    private int shopId;
    @JsonProperty(value = "fee")
    private Long fee;
    @JsonProperty(value = "to_name")
    private String toName;
    @JsonProperty(value = "to_phone")
    private String toPhone;
    @JsonProperty(value = "to_address")
    private String toAddress;
    @JsonProperty(value = "to_province")
    private String toProvince;
    @JsonProperty(value = "to_district")
    private String toDistrict;
    @JsonProperty(value = "to_ward")
    private String toWard;

}
