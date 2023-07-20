package com.example.ladiworkservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "account_warehouse")
public class AccountWarehouse extends BaseEntity {
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "warehouse_id")
    private Long warehouseId;
    @Column(name = "status")
    private int status = 1;
}
