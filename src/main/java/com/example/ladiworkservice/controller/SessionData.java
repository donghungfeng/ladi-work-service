package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.Finance;
import com.example.ladiworkservice.model.WebhookOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionData {
    private Finance finance;
    private List<WebhookOrder> orders;
}
