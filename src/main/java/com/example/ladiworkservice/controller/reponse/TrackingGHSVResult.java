package com.example.ladiworkservice.controller.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackingGHSVResult {
    @JsonProperty(value = "time")
    private String time;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "note")
    private String note;
}
