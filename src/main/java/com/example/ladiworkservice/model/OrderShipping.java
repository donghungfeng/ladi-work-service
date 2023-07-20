package com.example.ladiworkservice.model;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_ghsv")
public class OrderShipping extends BaseEntity{
    @Column(name = "order_code", length = 100)
    private String orderCode;

    @Column(name = "client_code", length = 100)
    private String clientCode;

    @Column(name = "note")
    private String note;

    @Column(name = "shop_id")
    private int shopId;

    @Column(name = "product_names")
    private String productNames;

    @Column(name = "`weight`")
    private int weight;

    @Column(name = "`length`")
    private int length;

    @Column(name = "`width`")
    private int width;

    @Column(name = "totalOrderValue")
    private Double totalOrderValue;

    @Column(name = "config_delivery")
    private int configDelivery;

    @Column(name = "is_return")
    private boolean isReturn;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "to_phone")
    private String toPhone;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "to_province")
    private String toProvince;

    @Column(name = "to_district")
    private String toDistrict;

    @Column(name = "to_ward")
    private String toWard;

    @ManyToMany(
        cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name = "order_product",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Product> products;
}
