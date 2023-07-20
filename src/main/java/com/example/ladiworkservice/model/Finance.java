package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "finance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Finance extends BaseEntity{
    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "date_to")
    private Long dateTo;

    @Column(name = "date_from")
    private Long dateFrom;

    @Column(name = "date_paid")
    private Long datePaid;

    @Column(name = "transfer_fee")
    private Long transferFee;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "total_fee")
    private Long totalFee;

    @Column(name = "total_order")
    private Long totalOrder;

    @Column(name = "amount_received")
    private Long amountReceived;

    @Column(name = "amount_collected")
    private Long amountCollected;


}
