package com.example.ladiworkservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "list_log_work")
@Data
@NoArgsConstructor
public class ListLogWork extends BaseEntity {
    @Column(name = "info_user")
    private String infoUser;

    @Column(name = "time")
    private Long time;

    @Column(name = "check_in_time")
    private Long checkInTime;

    @Column(name = "check_out_time")
    private Long checkOutTime;

    @Column(name = "log_work_type")
    private int logWorkType;

    @Column(name = "note")
    private String note;
}
