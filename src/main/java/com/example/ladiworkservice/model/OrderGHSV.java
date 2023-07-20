package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity(name = "orderr_ghsv")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderGHSV extends BaseEntity {
    @JoinColumn(name = "status")
    private String status;

    @JoinColumn(name = "client_code")
    private String clientCode;

    @JoinColumn(name = "required_code")
    private String requiredCode;

    @JoinColumn(name = "signpart")
    private Boolean signpart;

    @JoinColumn(name = "audit_code")
    private String auditCode;

    @JoinColumn(name = "delivery_time")
    private String deliveryTime;

    @JoinColumn(name = "cod")
    private int cod;

    @JoinColumn(name = "value")
    private int value;

    @JoinColumn(name = "weight")
    private int weight;

    @JoinColumn(name = "fee")
    private int fee;

    @JoinColumn(name = "to_name")
    private String toName;

    @JoinColumn(name = "to_phone")
    private String toPhone;

    @JoinColumn(name = "to_address")
    private String toAddress;

    @JoinColumn(name = "to_province")
    private String toProvince;

    @JoinColumn(name = "to_district")
    private String toDistrict;

    @JoinColumn(name = "to_ward")
    private String toWard;
}
