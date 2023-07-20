package com.example.ladiworkservice.dto;

import com.example.ladiworkservice.model.CallInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDto {
    private Long id;
    private Long timeIn;
    private Long timeOut;
    private int totalOrder;
    private int successOrder;
    private int processedOrder;
    private int onlyOrder;
    private int processedOnlyOrder;
    private String ghiChu;
    private int isActive;
    private int donThanhCong;
    private String shopCode;
    private AccountDto  account;
    private CallInfo callInfo;
}
