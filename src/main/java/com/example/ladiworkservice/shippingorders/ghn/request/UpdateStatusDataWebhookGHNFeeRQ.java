package com.example.ladiworkservice.shippingorders.ghn.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateStatusDataWebhookGHNFeeRQ {
    @JsonProperty(value = "CODFailedFee")
    private int codFailedFee;

    @JsonProperty(value = "CODFee")
    private int codFee;

    @JsonProperty(value = "Coupon")
    private int coupon;

    @JsonProperty(value = "DeliverRemoteAreasFee")
    private int deliverRemoteAreasFee;

    @JsonProperty(value = "DocumentReturn")
    private int documentReturn;

    @JsonProperty(value = "DoubleCheck")
    private int doubleCheck;

    @JsonProperty(value = "Insurance")
    private int insurance;

    @JsonProperty(value = "MainService")
    private int mainService;

    @JsonProperty(value = "PickRemoteAreasFee")
    private int pickRemoteAreasFee;

    @JsonProperty(value = "R2S")
    private int r2S;

    @JsonProperty(value = "Return")
    private int Return;

    @JsonProperty(value = "StationDO")
    private int stationDo;

    @JsonProperty(value = "StationPU")
    private int stationPu;

    @JsonProperty(value = "Total")
    private int total;
}
