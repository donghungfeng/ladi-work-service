package com.example.ladiworkservice.model;

import lombok.*;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "data_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataConfig extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "customer_info", columnDefinition = "text")
    private String customerInfo;

    @Column(name = "address_info", columnDefinition = "text")
    private String addressInfo;

    @Column(name = "product_info", columnDefinition = "text")
    private String productInfo;

    @Column(name = "note_info", columnDefinition = "text")
    private String noteInfo;
}
