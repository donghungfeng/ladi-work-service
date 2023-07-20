package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.SubProduct;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.SubProductRepository;
import com.example.ladiworkservice.service.SubProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubProductServiceImpl extends BaseServiceImpl<SubProduct> implements SubProductService {
    @Autowired
    SubProductRepository subProductRepository;
    @Override
    protected BaseRepository<SubProduct> getRepository() {
        return subProductRepository;
    }

    @Override
    public BaseResponse deleteById(Long id){
        SubProduct subProduct = subProductRepository.findAllById(id);
        if (subProduct == null){
            return new BaseResponse(500, "message", "Không tồn tại bản ghi với id tương ứng!");
        }
        subProduct.setStatus(0);
        subProductRepository.save(subProduct);
        return new BaseResponse(200, "message", "Xóa thành công");
    }
}
