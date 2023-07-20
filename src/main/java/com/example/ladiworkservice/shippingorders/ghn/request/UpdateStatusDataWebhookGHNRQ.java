package com.example.ladiworkservice.shippingorders.ghn.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStatusDataWebhookGHNRQ {
    @JsonProperty(value = "CODAmount")
    private int codAmount;

    @JsonProperty(value = "CODTransferDate")
    private String codTransferDate;

    @JsonProperty(value = "ClientOrderCode")
    private String clientOrderCode;

    @JsonProperty(value = "ConvertedWeight")
    private int convertedWeight;

    @JsonProperty(value = "Description")
    private String description;

    @JsonProperty(value = "Fee")
    private UpdateStatusDataWebhookGHNFeeRQ fee;

    @JsonProperty(value = "Height")
    private int height;

    @JsonProperty(value = "IsPartialReturn")
    private Boolean isPartialReturn;

    @JsonProperty(value = "Length")
    private int length;

    @JsonProperty(value = "OrderCode")
    private String orderCode;

    @JsonProperty(value = "PartialReturnCode")
    private String partialReturnCode;

    @JsonProperty(value = "PaymentType")
    private int paymentType;

    @JsonProperty(value = "Reason")
    private String reason;

    @JsonProperty(value = "ReasonCode")
    private String reasonCode;

    @JsonProperty(value = "ShopID;")
    private int shopID;

    @JsonProperty(value = "Status")
    private String status;

    @JsonProperty(value = "Time")
    private String time;

    @JsonProperty(value = "TotalFee")
    private int totalFee;

    @JsonProperty(value = "Type")
    private String type;

    @JsonProperty(value = "Warehouse")
    private String warehouse;

    @JsonProperty(value = "Weight")
    private int weight;

    @JsonProperty(value = "Width")
    private int width;
}
