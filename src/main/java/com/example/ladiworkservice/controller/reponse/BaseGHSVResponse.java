package com.example.ladiworkservice.controller.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseGHSVResponse {
    private Boolean success;
    private String msg;
    private Object shop;
    private Object shops;
    @JsonProperty("error")
    private String error;

    @JsonProperty("response_code")
    private int responseCode;
}
