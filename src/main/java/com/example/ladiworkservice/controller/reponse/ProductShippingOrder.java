package com.example.ladiworkservice.controller.reponse;

import com.example.ladiworkservice.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductShippingOrder {
    private Product product;
    private Long quantity;
    private Long price;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Product{
        private Long id;
        private String code;
        private Warehouse warehouse;
    }
}
