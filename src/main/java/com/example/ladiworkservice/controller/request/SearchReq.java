package com.example.ladiworkservice.controller.request;

import lombok.Data;

@Data
public class SearchReq {

    private String filter;
    private String sort;
    private Integer size;
    private Integer page;
}
