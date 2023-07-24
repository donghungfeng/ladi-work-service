package com.example.ladiworkservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "log_work")
@Data
@NoArgsConstructor
public class Log_work extends BaseEntity {

    @Column(name = "type")
    private int type;

    @Column(name = "time")
    private Long time;

    @Column(name = "message")
    private String message;

    @Column(name = "data_received")
    private String dataReceived;

    @Column(name = "data_sent")
    private String dataSent;

    @Column(name = "ip")
    private String ip;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
