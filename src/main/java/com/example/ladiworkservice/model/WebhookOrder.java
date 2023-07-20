package com.example.ladiworkservice.model;

import lombok.*;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "webhook_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WebhookOrder extends BaseEntity {

    @Column(name = "client_code")
    private String clientCode;

    @Column(name = "cod")
    private Long cod;

    @Column(name = "config_collect")
    private String configCollect;

    @Column(name = "date_approve")
    private Long dateApprove;

    @Column(name = "date_changed")
    private Long dateChanged;

    @Column(name = "date_create")
    private Long dateCreate;

    @Column(name = "date_expiration")
    private Long dateExpiration;

    @Column(name = "date_success")
    private Long dateSuccess;

    @Column(name = "fee")
    private Long fee;

    @Column(name = "length")
    private Double length;

    @Column(name = "note")
    private String note;

    @Column(name = "note_delay")
    private String noteDelay;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "product")
    private String product;

    @Column(name = "required_code")
    private String requiredCode;

    @Column(name = "shop_id")
    private Long shop;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "status_text")
    private String statusText;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "to_district")
    private String toDistrict;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "to_phone")
    private String toPhone;

    @Column(name = "to_province")
    private String toProvince;

    @Column(name = "to_ward")
    private String toWard;

    @Column(name = "value")
    private Long value;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "finance_id")
    private Long financeId;

    @Column(name = "date_financed")
    private Long dateFinanced;
}
