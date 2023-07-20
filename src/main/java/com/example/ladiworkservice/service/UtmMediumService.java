package com.example.ladiworkservice.service;

import com.example.ladiworkservice.dto.UtmMediumDto;
import com.example.ladiworkservice.model.UtmMedium;

import java.util.List;

public interface UtmMediumService extends BaseService<UtmMedium> {
    public List<UtmMediumDto> getAllData(String jwt);
}
