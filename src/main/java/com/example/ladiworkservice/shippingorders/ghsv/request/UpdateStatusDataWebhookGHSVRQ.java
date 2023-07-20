package com.example.ladiworkservice.shippingorders.ghsv.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateStatusDataWebhookGHSVRQ {

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "client_code")
    private String clientCode;

    @JsonProperty(value = "required_code")
    private String requiredCode;

    @JsonProperty(value = "signpart")
    private Boolean signpart;

    @JsonProperty(value = "audit_code")
    private String auditCode;

    @JsonProperty(value = "delivery_time")
    private String deliveryTime;

    @JsonProperty(value = "cod")
    private int cod;

    @JsonProperty(value = "value")
    private int value;

    @JsonProperty(value = "weight")
    private int weight;

    @JsonProperty(value = "fee")
    private int fee;

    @JsonProperty(value = "to_name")
    private String toName;

    @JsonProperty(value = "to_phone")
    private String toPhone;

    @JsonProperty(value = "to_address")
    private String toAddress;

    @JsonProperty(value = "to_province")
    private String toProvince;

    @JsonProperty(value = "to_district")
    private String toDistrict;

    @JsonProperty(value = "to_ward")
    private String toWard;
}
