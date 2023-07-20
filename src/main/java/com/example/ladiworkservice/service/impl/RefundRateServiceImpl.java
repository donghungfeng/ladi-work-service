package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.exceptions.FindByIdNullException;
import com.example.ladiworkservice.model.RefundRate;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.RefundRateRepository;
import com.example.ladiworkservice.service.RefundRateService;
import org.springframework.stereotype.Service;


@Service
public class RefundRateServiceImpl extends BaseServiceImpl<RefundRate> implements RefundRateService {
    private final RefundRateRepository refundRateRepository;

    public RefundRateServiceImpl(RefundRateRepository refundRateRepository) {
        this.refundRateRepository = refundRateRepository;
    }

    @Override
    public BaseResponse inupRefundRate(RefundRate refundRate) {
        BaseResponse response = new BaseResponse(200, "Thành công", null);
        try {
            response.RESULT = refundRateRepository.save(refundRate);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return response;
    }

    @Override
    public RefundRate findById(Long id) {
        RefundRate refundRate = new RefundRate();

        try {
            var result = refundRateRepository.findById(id);
            if(!result.isEmpty())
                refundRate = result.get();
        }
        catch (Exception exception) {
            throw new FindByIdNullException(exception.getMessage());
        }
        return refundRate;
    }



    @Override
    protected BaseRepository<RefundRate> getRepository() {
        return refundRateRepository;
    }
}
