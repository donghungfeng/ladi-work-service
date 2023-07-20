package com.example.ladiworkservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    @JsonProperty("shop_id")
    private int shopId;

    @JsonProperty("required_code")
    private String requiredCode;

    @JsonProperty("order_code")
    private String orderCode;

    @JsonProperty("client_code")
    private String clientCode;

    @JsonProperty("sorting_code")
    private String sortingCode;

    @JsonProperty("note")
    private String note;

    @JsonProperty("product")
    private String product;

    @JsonProperty("config_collect")
    private int configCollect;

    @JsonProperty("is_return")
    private boolean isReturn;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("cod")
    private int cod;

    @JsonProperty("code")
    private int code;

    @JsonProperty("value")
    private double value;

    @JsonProperty("fee")
    private int fee;

    @JsonProperty("length")
    private int length;

    @JsonProperty("width")
    private int width;

    @JsonProperty("to_name")
    private String toName;

    @JsonProperty("to_phone")
    private String toPhone;

    @JsonProperty("to_address")
    private String toAddress;

    @JsonProperty("to_province")
    private String toProvince;

    @JsonProperty("to_district")
    private String toDistrict;

    @JsonProperty("to_ward")
    private String toWard;

    @JsonProperty("partner_code")
    private String partnerCode;

    @JsonProperty("productId")
    private List<Long> productIds;
}
