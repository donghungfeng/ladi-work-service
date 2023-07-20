package com.example.ladiworkservice.controller.reponse;

import com.example.ladiworkservice.model.CallInfo;
import com.example.ladiworkservice.model.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CheckInResponse {
    private Work work;
    private CallInfo callInfo;
}
