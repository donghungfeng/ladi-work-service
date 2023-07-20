package com.example.ladiworkservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountShippingDto {
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Token không được để trống")
    @JsonProperty("token")
    private String token;

    @NotNull(message = "Tên shop không được để trống")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Số điện thoại không được để trống")
    @JsonProperty("phone")
    private String phone;

    @NotNull(message = "Tỉnh/thành không được để trống")
    @JsonProperty("province")
    private String province;

    @NotNull(message = "Quận/Huyện không được để trống")
    @JsonProperty("district")
    private String district;

    @NotNull(message = "Phường/Xã không được để trống")
    @JsonProperty("ward")
    private String ward;

    @NotNull(message = "Địa chỉ không được để trống")
    @JsonProperty("address")
    private String address;

    @JsonProperty("is_default")
    private boolean is_default = false;

    @JsonProperty("typeId")
    private Long typeId;
}
