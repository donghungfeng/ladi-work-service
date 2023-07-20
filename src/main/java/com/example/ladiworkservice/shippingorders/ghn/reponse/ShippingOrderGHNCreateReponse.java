package com.example.ladiworkservice.shippingorders.ghn.reponse;

import com.example.ladiworkservice.shippingorders.ghn.request.DataGHN;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShippingOrderGHNCreateReponse {
    private int code;
    private String code_message_value;
    private DataGHN data;
    private String message;
    private String message_display;
}
