package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.BoLDetail;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.BoLDetailRepository;
import com.example.ladiworkservice.service.BoLDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoLDetailServiceImpl  extends BaseServiceImpl<BoLDetail> implements BoLDetailService {
    @Autowired
    BoLDetailRepository boLDetailRepository;


    @Override
    protected BaseRepository<BoLDetail> getRepository() {
        return boLDetailRepository;
    }

    @Override
    public BaseResponse deleteById(Long id){
        return new BaseResponse(200, "OK", "Api này không thực hiện chức năng gì!");
    }
}
