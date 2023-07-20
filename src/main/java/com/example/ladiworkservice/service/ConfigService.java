package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.FindConfigByCODERequest;
import com.example.ladiworkservice.model.Config;

public interface ConfigService extends BaseService<Config> {
    public BaseResponse getByCODE(FindConfigByCODERequest findConfigByCODERequest);

    public Config getByCode(String code);

    public BaseResponse getConfigByDate(String startDate, String endDate);

    Long getChiPhiVanChuyen();
}
