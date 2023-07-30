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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        if (logWorkRequest.getType() == 1) {
            return checkInOnsite(logWorkRequest);
        } else {
            return checkInRemote(logWorkRequest);
        }
    }

    private BaseResponse checkInOnsite(Log_workRequest logWorkRequest) {
        try {
            Location location = locationRepository.findAllLocationByUnitAndIp(logWorkRequest.getUnitId(), logWorkRequest.getIp());
            if(unitRepository.findUnitById(logWorkRequest.getUnitId())==null){
                return new BaseResponse(500, "Nhân viên không thuộc công ty không thực hiện được chấm công", null);
            }
            if (location != null) {
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                Long time = Long.parseLong(formatter.format(today));

                Gson gson = new Gson();
                Data_received dataReceived = new Data_received(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode(), time, logWorkRequest.getAddress(),logWorkRequest.getUnitId());
                Data_sent dataSent = new Data_sent(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode(),logWorkRequest.getUnitId());
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
        if(unitRepository.findUnitById(logWorkRequest.getUnitId())==null){
            return new BaseResponse(500, "Nhân viên không thuộc công ty không thực hiện được chấm công", null);
        }
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long time = Long.parseLong(formatter.format(today));
        Gson gson = new Gson();
        Data_received dataReceived = new Data_received(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode(), time, logWorkRequest.getAddress(),logWorkRequest.getUnitId());
        Data_sent dataSent = new Data_sent(logWorkRequest.getEmployeeName(), logWorkRequest.getEmployeeCode(),logWorkRequest.getUnitId());
        String data_received = gson.toJson(dataReceived);
        String data_sent = gson.toJson(dataSent);
        Log_work logWork = modelMapper.map(logWorkRequest, Log_work.class);
        logWork.setDataReceived(data_received);
        logWork.setDataSent(data_sent);
        logWork.setTime(time);
        logWork.setUnit(unitRepository.findUnitById(logWorkRequest.getUnitId()));
        //Thêm location khi remote
        Location location = modelMapper.map(logWorkRequest, Location.class);
        location.setName(logWorkRequest.getLocationName());
        location.setAddress(logWorkRequest.getAddress());
        location.setIp(logWorkRequest.getIp());
        location.setStatus(logWorkRequest.getStatus());
        location.setSecretKey(logWorkRequest.getSecretKey());

        if (locationRepository.findAllLocationByIp(location.getIp()) == null) {
            locationRepository.save(location);
        }
        logWork.setLocation(locationRepository.findAllLocationByIp(location.getIp()));
        return new BaseResponse(200, "OK", logWorkRepository.save(logWork));
    }

    @Override
    public BaseResponse findLogWorkByUser(String code, Long unitId,Long startDate,Long endDate, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<Log_work> logWorkList = logWorkRepository.findLogWorkByUser(code, unitId,startDate,endDate);
        if (!logWorkList.isEmpty()) {
            // Thực hiện phân trang dựa trên danh sách kết quả và thông tin phân trang đã có
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), logWorkList.size());
            Page<Log_work> logWorkPage = new PageImpl<>(logWorkList.subList(start, end), pageable, logWorkList.size());
            return new BaseResponse(200, "OK", logWorkPage.getContent());
        } else {
            return new BaseResponse(500, "Không tìm thấy dữ liệu phù hợp", null);
        }
    }

}

