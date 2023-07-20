package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "product_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategory extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "properties")
    private String properties;

    @Column(name = "product_id")
    private Long productId;

}


