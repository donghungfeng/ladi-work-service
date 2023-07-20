package com.example.ladiworkservice.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetPriceImportRequest {

    private List<UpdateSoldProductRequest> list;

    public GetPriceImportRequest(List<UpdateSoldProductRequest> list) {
        this.list = list;
    }

    public GetPriceImportRequest() {
    }
}
