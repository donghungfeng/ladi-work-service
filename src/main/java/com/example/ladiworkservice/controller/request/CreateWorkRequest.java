package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWorkRequest {
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
    private Long nhanVienId;
    private Long line;
}
