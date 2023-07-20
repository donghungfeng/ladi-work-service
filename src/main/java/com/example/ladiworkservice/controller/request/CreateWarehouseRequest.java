package com.example.ladiworkservice.controller.request;

import com.example.ladiworkservice.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateWarehouseRequest {
    private Warehouse warehouse;
    private List<Long> staffIdList;
}
