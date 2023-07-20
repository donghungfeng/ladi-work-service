package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateListOrderGHSVRequest {
    private List<OrderGHSVRequest> orderGHSVRequests;
    private Long shopId;
}
