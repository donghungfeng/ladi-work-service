package com.example.ladiworkservice.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WardGHSVResponse  extends BaseGHSVResponse {
    private Object wards;
}
