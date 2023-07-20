package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateShopGHSVRequest {
    @NotNull(message = "Token không được để trống")
    private String token;

    @NotNull(message = "Tên shop không được để trống")
    private String name;

    @NotNull(message = "Số điện thoại không được để trống")
    private String phone;

    @NotNull(message = "Tỉnh/thành không được để trống")
    private String province;

    @NotNull(message = "Quận/Huyện không được để trống")
    private String district;

    @NotNull(message = "Phường/Xã không được để trống")
    private String ward;

    @NotNull(message = "Địa chỉ không được để trống")
    private String address;

    @NotNull(message = "Loại liên kết không được để trống")
    private Long typeId;

    private boolean is_default = false;
}
