package com.example.ladiworkservice.shippingorders.ghsv.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShippingOrderGHSVCreateBody {
    private int shop_id;
    private String product;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int price;
    private int value;
    private int config_delivery;
    private int config_collect;
    private int is_return;
    private String client_code;
    private String note;
    private String to_name;
    private String to_phone;
    private String to_address;
    private String to_province;
    private String to_district;
    private String to_ward;
    private String source;
}
