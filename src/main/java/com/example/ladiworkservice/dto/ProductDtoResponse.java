package com.example.ladiworkservice.dto;

import com.example.ladiworkservice.model.OrderShipping;
import com.example.ladiworkservice.model.ProductCategory;
import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.model.SubProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDtoResponse {
    private Long id;
    private String code;
    private String name;
    private int status;
    private String shopcode;
    private String note;
    private Double giaBan;
    private Double giaNhap;
    private List<OrderShipping> orders;
    private String image;
    private String properties;
    private Long createAt;
    private Long updateAt;
    private Shop shop;
    private ProductCategory productCategory;
    private Long warehouseId;
    private List<SubProduct> subProductList;
}
