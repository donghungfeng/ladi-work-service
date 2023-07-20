package com.example.ladiworkservice.controller.request;

import com.example.ladiworkservice.model.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckInRequest {
    private Work work;
    private Long line;
}
