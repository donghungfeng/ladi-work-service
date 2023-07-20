package com.example.ladiworkservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "statistics_revenue")
public class StatisticsRevenue extends BaseEntity{
    @Column(name = "date")
    private Long date;

    @Column(name = "shipping_cost")
    private Long shippingCost = 0L;

    @Column(name = "cost_price")
    private Long costPrice = 0L;

    @Column(name = "operating_cost")
    private Double operatingCost = 0.0;

    @Column(name = "mkt_cost")
    private Double mktCost = 0.0;

    @Column(name = "other_cost")
    private Double otherCost = 0.0;

    @Column(name = "revenue")
    private Long revenue = 0L;

    @Column(name = "profit")
    private Double profit = 0.0;

    @Column(name = "total_order")
    private Long totalOrder = 0L;

    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;

}
