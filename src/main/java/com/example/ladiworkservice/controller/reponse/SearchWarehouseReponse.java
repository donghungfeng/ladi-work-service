package com.example.ladiworkservice.controller.reponse;

import com.example.ladiworkservice.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SearchWarehouseReponse {
    private Long id;
    private String code;
    private String name;
    private String address;
    private String phone;
    private Long createAt;
    private Long updateAt;
    private Shop shop;
    private int flag;
    private int staus;
    private List<Long> staffIdList;
}
