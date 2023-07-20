package com.example.ladiworkservice.shippingorders.ghsv.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateStatusDataWebhookGHSVRP {
    private Boolean success;
    private String msg;
}
