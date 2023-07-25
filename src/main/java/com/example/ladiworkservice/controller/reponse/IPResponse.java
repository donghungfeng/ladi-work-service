package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPResponse {
    private String Ip;
    private String wifiName;
}
