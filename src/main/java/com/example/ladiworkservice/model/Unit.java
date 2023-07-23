package com.example.ladiworkservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "unit")
public class Unit extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "url")
    private String url;
    @Column(name = "status")
    private int status;
    @Column(name = "time_start")
    private Long timeStart;
    @Column(name = "time_end")
    private Long timeEnd;

}
