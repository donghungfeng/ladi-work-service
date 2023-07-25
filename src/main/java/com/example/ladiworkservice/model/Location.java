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
@Table(name = "location")
public class Location extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "ip")
    private String ip;
    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "status")
    private int status;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    Unit unit;
}
