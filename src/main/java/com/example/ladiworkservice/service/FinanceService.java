package com.example.ladiworkservice.service;

import com.example.ladiworkservice.model.Finance;
import org.springframework.web.multipart.MultipartFile;

public interface FinanceService extends BaseService<Finance> {

    void session(Long shippingTypeId, MultipartFile file) throws Exception;
}
