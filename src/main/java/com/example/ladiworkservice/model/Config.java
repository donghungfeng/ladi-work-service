package com.example.ladiworkservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "config")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Config extends BaseEntity{
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "status")
    private int status;

    @Column(name = "note")
    private String note;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "from_date")
    private Long fromDate;

    @Column(name = "to_date")
    private Long toDate;

    @ManyToOne
    @JoinColumn(name = "shop", nullable = true)
    private Shop shop;
}
