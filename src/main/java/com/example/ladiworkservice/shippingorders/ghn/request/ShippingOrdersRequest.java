package com.example.ladiworkservice.shippingorders.ghn.request;

import com.example.ladiworkservice.controller.request.ghsvRequest.ShippingOrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShippingOrdersRequest {
    private Long accountShippingId;
    private int shopId;
    private List<ShippingOrderItem> shippingOrderItems;
    private int configDelivery;
    private int configCollect;
    private String noteShipping;
}
