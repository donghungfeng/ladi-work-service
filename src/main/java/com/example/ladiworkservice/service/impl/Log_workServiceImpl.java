package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.Log_workRequest;
import com.example.ladiworkservice.model.Data_received;
import com.example.ladiworkservice.model.Data_sent;
import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.model.Log_work;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.LocationRepository;
import com.example.ladiworkservice.repository.Log_workRepository;
import com.example.ladiworkservice.repository.UnitRepository;
import com.example.ladiworkservice.service.Log_workService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class Log_workServiceImpl extends BaseServiceImpl<Log_work> implements Log_workService {
    @Autowired
    Log_workRepository logWorkRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    protected BaseRepository<Log_work> getRepository() {
        return logWorkRepository;
    }

    @Override
    public BaseResponse checkIn(Log_workRequest logWorkRequest) {
        if (logWorkRequest.getType() == 0) {
            return checkInOnsite(logWorkRequest);
        } else {
            return checkInRemote(logWorkRequest);
        }
    }

    private BaseResponse checkInOnsite(Log_workRequest logWorkRequest) {
        try {
            Location location = locationRepository.findAllLocationByUnitAndName(logWorkRequest.getUnitId(), logWorkRequest.getLocationName());
            String locationIP = location.getIp();
            if (locationIP.equals(logWorkRequest.getIp())) {
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                Long time = Long.parseLong(formatter.format(today));

                Gson gson = new Gson();
                Data_received dataReceived = new Data_received(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode(), time, logWorkRequest.getAddress());
                Data_sent dataSent = new Data_sent(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode());
                String data_received = gson.toJson(dataReceived);
                String data_sent = gson.toJson(dataSent);

                Log_work logWork = modelMapper.map(logWorkRequest, Log_work.class);
                logWork.setTime(time);
                logWork.setDataReceived(data_received);
                logWork.setDataSent(data_sent);
                logWork.setLocation(location);
                logWork.setUnit(unitRepository.findUnitById(logWorkRequest.getUnitId()));
                return new BaseResponse(200, "OK", logWorkRepository.save(logWork));
            } else {
                return new BaseResponse(500, "Không tồn tại Wifi thuộc công ty", null);
            }
        } catch (Exception e) {
            return new BaseResponse(500, "Không tồn tại Wifi thuộc công ty", null);
        }
    }

    private BaseResponse checkInRemote(Log_workRequest logWorkRequest) {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long time = Long.parseLong(formatter.format(today));
        Gson gson = new Gson();
        Data_received dataReceived = new Data_received(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode(), time, logWorkRequest.getAddress());
        Data_sent dataSent = new Data_sent(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode());
        String data_received = gson.toJson(dataReceived);
        String data_sent = gson.toJson(dataSent);
        Log_work logWork = modelMapper.map(logWorkRequest, Log_work.class);
        logWork.setDataReceived(data_received);
        logWork.setDataSent(data_sent);
        logWork.setTime(time);
        //Thêm location khi remote
        Location location = modelMapper.map(logWorkRequest, Location.class);
        location.setName(logWorkRequest.getLocationName());
        location.setAddress(logWorkRequest.getAddress());
        location.setIp(logWorkRequest.getIp());
        location.setStatus(logWorkRequest.getStatus());
        location.setSecretKey(logWorkRequest.getSecretKey());
        locationRepository.save(location);
        logWork.setLocation(locationRepository.findAllLocationByName(location.getName()));
        return new BaseResponse(200, "OK", logWorkRepository.save(logWork));
    }
}

