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
    @Column(name = "data_received")
    private String dataReceived;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "time")
    private Long time;

    @Column(name = "check_in_time")
    private String checkInTime;

    @Column(name = "check_out_time")
    private String checkOutTime;

    @Column(name = "total_log_time")
    private double totalLogTime;

    @Column(name = "log_work_type")
    private int logWorkType;

    @Column(name = "note")
    private String note;

    @Column(name = "unitName")
    private String unitName;
}
