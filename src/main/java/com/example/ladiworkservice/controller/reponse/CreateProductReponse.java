package com.example.ladiworkservice.controller.reponse;

import com.example.ladiworkservice.controller.request.CreateSubProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductReponse {
    private Long id;
    private int status;
    private String image;
    private String code;
    private String name;
    private String categoryId;
    private int subProductQuantity;
    private Long giaBan;
    private List<CreateSubProductRequest> subProductRequestList;
}
