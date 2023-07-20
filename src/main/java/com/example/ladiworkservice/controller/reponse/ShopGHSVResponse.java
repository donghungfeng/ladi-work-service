package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopGHSVResponse {
    private String id;
    private String name;
    private String phone;
    private String province;
    private String district;
    private String ward;
    private String address;
    private boolean isDefault;
}
