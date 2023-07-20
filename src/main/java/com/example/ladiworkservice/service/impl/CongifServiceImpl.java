package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.constants.ConfigCode;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.FindConfigByCODERequest;
import com.example.ladiworkservice.model.Config;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.ConfigRepository;
import com.example.ladiworkservice.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CongifServiceImpl extends BaseServiceImpl<Config> implements ConfigService {
    @Autowired
    ConfigRepository configRepository;

    @Override
    protected BaseRepository<Config> getRepository() {
        return configRepository;
    }

    @Override
    public BaseResponse getByCODE(FindConfigByCODERequest findConfigByCODERequest) {
        Config config = configRepository.findAllByCode(findConfigByCODERequest.getCode());
        if (config == null) {
            return new BaseResponse(500, "Congif Not Found", null);
        }
        return new BaseResponse(200, "OK", config);
    }

    @Override
    public Config getByCode(String code) throws RuntimeException {
        Config config = new Config();
        config = configRepository.findAllByCode(code);


        if (config == null) {
            return config;
        }
        return config;
    }

    @Override
    public BaseResponse getConfigByDate(String startDate, String endate) {
        return new BaseResponse(200, "OK", configRepository.findConfigByDate(Long.parseLong(startDate), Long.parseLong(endate)));
    }

    @Override
    public Long getChiPhiVanChuyen() {
        Config config = this.findByCode(ConfigCode.CHI_PHI_VAN_CHUYEN);
        return Long.valueOf(config.getValue());
    }

    private Config findByCode(String code) {
        return configRepository.getByCode(code).orElseThrow(
                () -> new RuntimeException(String.format("Hệ thống chưa có cấu hình: %s", code))
        );
    }
}
