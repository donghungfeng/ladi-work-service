package com.example.ladiworkservice.model;

import lombok.*;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {
    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "province")
    private String province;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "district")
    private String district;

    @Column(name = "commune_id")
    private Long communeId;

    @Column(name = "commune")
    private String commune;
}
