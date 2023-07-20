package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account_shipping_group")
public class AccountShippingGroup extends BaseEntity{
//    @Column(name = "code", unique = true)
//    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private int status;

    @Column(name = "is_default")
    private int isDefault = 0;

    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;
}
