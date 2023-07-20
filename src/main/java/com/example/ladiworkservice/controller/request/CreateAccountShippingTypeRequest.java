package com.example.ladiworkservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountShippingTypeRequest {
    private String name;
    private String note;
}
