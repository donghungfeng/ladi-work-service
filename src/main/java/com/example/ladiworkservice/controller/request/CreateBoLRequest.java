package com.example.ladiworkservice.controller.request;

import com.example.ladiworkservice.model.BoL;
import com.example.ladiworkservice.model.BoLDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoLRequest {
    private BoL boL;
    private List<BoLDetail> boLDetailList;
}
