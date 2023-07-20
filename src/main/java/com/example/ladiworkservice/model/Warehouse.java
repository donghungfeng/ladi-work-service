package com.example.ladiworkservice.model;

import com.example.ladiworkservice.configurations.global_variable.WAREHOUSE;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Warehouse extends BaseEntity{
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "update_at")
    private Long updateAt;

    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;

    @Column(name = "flag")
    private int flag = WAREHOUSE.FLAG_NOMAL;

    @Column(name = "status")
    private int staus = WAREHOUSE.STATUS_ACTIVATED;



}
