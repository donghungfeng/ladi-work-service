package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.global_variable.DATA;
import com.example.ladiworkservice.service.ConvertStatusShippingToData;
import org.springframework.stereotype.Service;

@Service
public class ConvertStatusShippingToDataImpl implements ConvertStatusShippingToData {
    @Override
    public int gHSV(String statusShipping) {
        switch (statusShipping){
            //Đang Chuyển Kho Giao
            case "Đang Chuyển Kho Giao": case "Đã Chuyển Kho Giao":
                return DATA.IN_TRANSIT;

            //Đang Giao Hàng
            case "Đang Vận Chuyển": case "Đang Giao Hàng":
                return DATA.ON_DELIVERY;

            //Hoãn Giao Hàng
            case "Hoãn Giao Hàng": case "Không Giao Được":
                return DATA.DELAY_DELIVERY;

            //Đã Giao Hàng
            case "Đã giao hàng":
                return DATA.DELIVERED;

            //Đã Đối Soát Giao Hàng
            case "Đã Đối Soát Giao Hàng":
                return DATA.DELIVERY_VERIFIED;

            //Xác Nhận Hoàn
            case "Xác Nhận Hoàn":
                return DATA.REFUND_CONFIRM;

            //Hoãn Trả Hàng
            case "Hoãn Trả Hàng":
                return DATA.DELAY_RETURNS;

            //Đang chuyển trả kho
            case "Đang Chuyển Kho Trả": case "Đã Chuyển Kho Trả Toàn Bộ": case "Đã Chuyển Kho Trả Một Phần": case "Đang Trả Hàng":
            case "Đã Trả Hàng Một Phần":
                return DATA.RETURNING_WAREHOUSE;

            //Đã Trả Hàng Toàn Bộ
            case "Đã Đối Soát Trả Hàng": case "Đã Trả Hàng Toàn Bộ":
                return DATA.PAID_IN_FULL;

            //Hủy
            case "Hủy":
                return DATA.CANCEL_ORDER;

        }
        return 0;
    }
}
