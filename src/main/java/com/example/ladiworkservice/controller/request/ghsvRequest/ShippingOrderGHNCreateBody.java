package com.example.ladiworkservice.controller.request.ghsvRequest;

import com.example.ladiworkservice.shippingorders.ghn.request.Item;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShippingOrderGHNCreateBody {
    private int payment_type_id;
    private String note;
    private String from_name;
    private String from_phone;
    private String from_address;
    private String from_ward_name;
    private String from_district_name;
    private String from_province_name;
    private String required_note;
    private String return_name;
    private String return_phone;
    private String return_address;
    private String return_ward_name;
    private String return_district_name;
    private String return_province_name;
    private String client_order_code;
    private String to_name;
    private String to_phone;
    private String to_address;
    private String to_ward_name;
    private String to_district_name;
    private String to_province_name;
    private int cod_amount;
    private String content;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int cod_failed_amount;
    private int pick_station_id;
    private int insurance_value;
    private int service_id;
    private int service_type_id;
    private String coupon;
    private String pick_shift;
    private Long pickup_time;
    private List<Item> items;
}
