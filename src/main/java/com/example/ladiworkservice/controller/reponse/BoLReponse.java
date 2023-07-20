package com.example.ladiworkservice.controller.reponse;

import com.example.ladiworkservice.model.BoLDetail;
import com.example.ladiworkservice.model.Warehouse;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BoLReponse {
    private Long id;
    private Long createdId;
    private String supplierId;
    private int type;
    private int status;
    private Long createAt;
    private Long updateAt;
    private Warehouse warehouse;
    private List<BoLDetail> boLDetails;
}
