package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accountShipping_type")
public class AccountShippingType  extends BaseEntity {
    @Column(name = "id")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "url")
    private String url;
}
