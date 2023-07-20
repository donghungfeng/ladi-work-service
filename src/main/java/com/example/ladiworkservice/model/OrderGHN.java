package com.example.ladiworkservice.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity(name = "order_ghn")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderGHN extends BaseEntity{
    @JoinColumn(name = "CODAmount")
    private int codAmount;

    @JoinColumn(name = "CODTransferDate")
    private String codTransferDate;

    @JoinColumn(name = "ClientOrderCode")
    private String clientOrderCode;

    @JoinColumn(name = "ConvertedWeight")
    private int convertedWeight;

    @JoinColumn(name = "Description")
    private String description;

    @JoinColumn(name = "Height")
    private int height;

    @JoinColumn(name = "IsPartialReturn")
    private Boolean isPartialReturn;

    @JoinColumn(name = "Length")
    private int length;

    @JoinColumn(name = "OrderCode")
    private String orderCode;

    @JoinColumn(name = "PartialReturnCode")
    private String partialReturnCode;

    @JoinColumn(name = "PaymentType")
    private int paymentType;

    @JoinColumn(name = "Reason")
    private String reason;

    @JoinColumn(name = "ReasonCode")
    private String reasonCode;

    @JoinColumn(name = "ShopID;")
    private int shopID;

    @JoinColumn(name = "Status")
    private String status;

    @JoinColumn(name = "Time")
    private String time;

    @JoinColumn(name = "TotalFee")
    private int totalFee;

    @JoinColumn(name = "Type")
    private String type;

    @JoinColumn(name = "Warehouse")
    private String warehouse;

    @JoinColumn(name = "Weight")
    private int weight;

    @JoinColumn(name = "Width")
    private int width;
}
