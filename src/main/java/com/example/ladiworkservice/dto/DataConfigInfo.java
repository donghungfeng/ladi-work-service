package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataConfigInfo {

    private String label;
    private String key;
    private Integer priority;
    private Integer require;
    private Integer editable;

    public static List<DataConfigInfo> getDefaultCustomer(){
        DataConfigInfo name = getDefault("Tên khách hàng", "name",1);
        DataConfigInfo phone = getDefault("Số điện thoại", "phone", 2);
        return Arrays.asList(name, phone);
    }

    public static List<DataConfigInfo> getDefaultAddress(){
        DataConfigInfo province = getDefault("Tỉnh", "province",1);
        DataConfigInfo district = getDefault("Huyện", "district", 2);
        DataConfigInfo ward = getDefault("Xã", "ward", 3);
        DataConfigInfo address = getDefault("Địa chỉ", "address", 4);
        return Arrays.asList(province, district, ward, address);
    }

    private static DataConfigInfo getDefault(String label, String key, Integer priority){
         return DataConfigInfo.builder()
                 .priority(priority)
                .editable(0)
                .require(1)
                .label(label)
                .key(key)
                .build();
    }
}
