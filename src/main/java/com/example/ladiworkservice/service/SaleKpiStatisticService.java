package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.SaleKpiStatisticRequest;
import com.example.ladiworkservice.model.SalerKpiStatistics;
import org.springframework.stereotype.Service;

@Service
public interface SaleKpiStatisticService extends BaseService<SalerKpiStatistics>{
    void updateSalerKpiStatistics(Long date, String shopCode, Long saleID);

    BaseResponse getSalerKpiStatistics(SaleKpiStatisticRequest req) ;
}
