package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log_workRequest {
    private String employeeName;
    private String employeeCode;
    private String locationName;
    private String address;
    private String secretKey;
    private Long unitId;
    private int type;
    private Long time;
    private String message;
    private String ip;
    private int status;
}
