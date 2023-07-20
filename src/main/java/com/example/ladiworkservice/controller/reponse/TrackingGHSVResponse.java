package com.example.ladiworkservice.controller.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrackingGHSVResponse {
    @JsonProperty(value = "success")
    private Boolean success;
    @JsonProperty(value = "msg")
    private String msg;
    @JsonProperty(value = "tracking")
    private List<Tracking> trackingList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Tracking{
        @JsonProperty(value = "time")
        private String time;

        @JsonProperty(value = "status")
        private String status;

        @JsonProperty(value = "address")
        private String address;

        @JsonProperty(value = "note")
        private String note;
    }
}

