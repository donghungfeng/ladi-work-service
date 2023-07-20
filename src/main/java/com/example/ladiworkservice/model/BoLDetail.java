package com.example.ladiworkservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "BoL_detail")
public class BoLDetail extends BaseEntity {
    // @Column(name = "BoL_id")
    // private Long boLId;

    @ManyToOne
    @JoinColumn(name = "bol")
    @JsonIgnore
    private BoL boL;

    // @Column(name = "sub_product_id")
    // private Long subProductId;

    @ManyToOne
    @JoinColumn(name = "sub_product")
    private SubProduct subProduct;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "available_quantity")
    private Long availableQuantity;

    @Column(name = "tranport_fee")
    private Long tranportFee = 0L;

    @Column(name = "discount")
    private Long discount = 0L;

    @Column(name = "tciq")
    private Long totalImport;

    @Column(name = "note")
    private String note;

    @Column(name = "price")
    private Long price = 0L;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "total_price")
    private Long totalPrice = 0L;

    @Column(name = "update_at")
    private Long updateAt;
}
