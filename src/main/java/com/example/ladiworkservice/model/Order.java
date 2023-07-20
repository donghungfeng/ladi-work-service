package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "webhook_order")
public class Order extends BaseEntity{
    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "weight")
    private Double weight = 0.0;

    @Column(name = "width")
    private Double width = 0.0;

    @Column(name = "length")
    private Double length = 0.0;

    @Column(name = "height")
    private Double height;

    @Column(name = "value")
    private Long value = 0L;

    @Column(name = "cod")
    private Long cod = 0L;

    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "note")
    private String note;

    @Column(name = "note_delay")
    private String noteDelay;

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

    @Column(name = "fee")
    private Long fee = 0L;

    @Column(name = "status")
    private int status;

    @Column(name = "status_text")
    private String statusText;

    @Column(name = "client_code")
    private String clientCode;

    @Column(name = "required_code")
    private String requiredCode;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "config_collect")
    private String configCollect;

    @Column(name = "date_create")
    private Long dateCreate;

    @Column(name = "date_approve")
    private Long dateApprove;

    @Column(name = "date_success")
    private Long dateSuccess;

    @Column(name = "date_expiration")
    private Long dateExpiration;

    @Column(name = "date_changed")
    private Long dateChange;

    @Column(name = "date_financed")
    private Long dateFinanced;

    @ManyToOne
    @JoinColumn(name = "finance_id")
    private Finance finance;
}
