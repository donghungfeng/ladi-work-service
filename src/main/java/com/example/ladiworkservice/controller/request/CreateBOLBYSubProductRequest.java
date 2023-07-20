package com.example.ladiworkservice.controller.request;

import com.example.ladiworkservice.model.SubProduct;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBOLBYSubProductRequest {
    private Long createdId;
    private String supplierInfo;
    private int type = 1;
    private int status;
    private Long tranportFee;
    private SubProduct subProduct;
}
