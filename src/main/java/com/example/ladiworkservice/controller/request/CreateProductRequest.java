package com.example.ladiworkservice.controller.request;

import com.example.ladiworkservice.model.Product;
import com.example.ladiworkservice.model.SubProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private Product product;
    private List<SubProduct> subProductList;
}
