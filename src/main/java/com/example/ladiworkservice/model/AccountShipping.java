package com.example.ladiworkservice.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account_shipping")
public class AccountShipping extends BaseEntity{
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "token")
    private String token;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "address")
    private String address;

    @Column(name = "is_default")
    private int isDefault;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccountShippingType accountShippingType;

    @ManyToOne
    @JoinColumn(name = "account_shipping_group")
    private AccountShippingGroup accountShippingGroup;
}
