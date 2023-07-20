package com.example.ladiworkservice.shippingorders.ghn.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Item {
    private String name;
    private String code;
    private int quantity;
    private int price;
    private int length;
    private int width;
    private int height;
}
