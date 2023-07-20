package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGHSVRequest {
    private int shop_id;
    private String client_code;
    private String note;

    @NotNull(message = "Sản phẩm không được để trống")
    private String product;

    @NotNull(message = "Khối lượng không được để trống")
    private int weight;
    private int length;
    private int width;
    private Double price;
    private Double value;

    @NotNull(message = "Cấu hình giao hàng không được để trống")
    private int config_delivery;

    @NotNull(message = "Cấu hình thu hộ không được để trống")
    private int config_collect;
    private boolean is_return;

    @NotNull(message = "Tên người nhận không được để trống")
    private String to_name;

    @NotNull(message = "Số điện thoại không được để trống")
    private String to_phone;

    @NotNull(message = "Địa chỉ không được để trống")
    private String to_address;

    @NotNull(message = "Tỉnh/Thành phố không được để trống")
    private String to_province;

    @NotNull(message = "Phường/xã không được để trống")
    private String to_district;

    @NotNull(message = "Quận/Huyện không được để trống")
    private String to_ward;
    private String source;
    private List<Long> productId;
    private com.example.ladiworkservice.model.Data data;
}

