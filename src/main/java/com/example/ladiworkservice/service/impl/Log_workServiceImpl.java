package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.Log_workRequest;
import com.example.ladiworkservice.model.*;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.Log_workService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    ListLogWorkRepository listLogWorkRepository;
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

                Date timeToday = new Date();
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                formatter1.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                String time1 = String.valueOf(formatter1.format(timeToday));
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

                ListLogWork logWorkResult = listLogWorkRepository.findUserWithCheckedIn(logWorkRequest.getEmployeeCode());
                ListLogWork logWorkCheckOutResult = listLogWorkRepository.findUserWithCheckedOut(logWorkRequest.getEmployeeCode());
                if(logWorkResult != null) {
                    logWorkResult.setCheckOutTime(time1);
                    logWorkResult.setNote(logWorkRequest.getMessage());
                    Date checkInTime = new Date(logWorkResult.getCheckInTime());
                    Date checkOutTime = new Date(time1);
                    double hours = calculateDuration(checkInTime, checkOutTime);
                    System.out.println(hours);
                    logWorkResult.setTotalLogTime(hours);
                    listLogWorkRepository.save(logWorkResult);
                } else if(logWorkCheckOutResult != null) {
                    logWorkCheckOutResult.setCheckOutTime(time1);
                    logWorkCheckOutResult.setNote(logWorkRequest.getMessage());
                    Date checkInTime = new Date(logWorkCheckOutResult.getCheckInTime());
                    Date checkOutTime = new Date(time1);
                    double hours = calculateDuration(checkInTime, checkOutTime);
                    logWorkCheckOutResult.setTotalLogTime(hours);
                    listLogWorkRepository.save(logWorkCheckOutResult);
                } else {
                    ListLogWork listLogWork = modelMapper.map(logWorkRequest, ListLogWork.class);
                    listLogWork.setCheckInTime(time1);
                    listLogWork.setTime(time);
                    listLogWork.setUserName(logWorkRequest.getEmployeeCode());
                    listLogWork.setFullName(logWorkRequest.getEmployeeName());
                    listLogWork.setDataReceived(data_received);
                    listLogWork.setLogWorkType(logWorkRequest.getType());
                    listLogWork.setNote(logWorkRequest.getMessage());
                    listLogWorkRepository.save(listLogWork);
                }
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

        Date timeToday = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter1.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String time1 = String.valueOf(formatter1.format(timeToday));

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

        ListLogWork logWorkResult = listLogWorkRepository.findUserWithCheckedIn(logWorkRequest.getEmployeeCode());
        ListLogWork logWorkCheckOutResult = listLogWorkRepository.findUserWithCheckedOut(logWorkRequest.getEmployeeCode());
        if(logWorkResult != null) {
            logWorkResult.setCheckOutTime(time1);
            logWorkResult.setNote(logWorkRequest.getMessage());
            Date checkInTime = new Date(logWorkResult.getCheckInTime());
            Date checkOutTime = new Date(time1);
            double hours = calculateDuration(checkInTime, checkOutTime);
            logWorkResult.setTotalLogTime(hours);
            System.out.println(hours);
            listLogWorkRepository.save(logWorkResult);
        } else if(logWorkCheckOutResult != null) {
            logWorkCheckOutResult.setCheckOutTime(time1);
            logWorkCheckOutResult.setNote(logWorkRequest.getMessage());
            Date checkInTime = new Date(logWorkCheckOutResult.getCheckInTime());
            Date checkOutTime = new Date(time1);
            double hours = calculateDuration(checkInTime, checkOutTime);
            logWorkCheckOutResult.setTotalLogTime(hours);
            listLogWorkRepository.save(logWorkCheckOutResult);
        } else {
            ListLogWork listLogWork = modelMapper.map(logWorkRequest, ListLogWork.class);
            listLogWork.setCheckInTime(time1);
            listLogWork.setTime(time);
            listLogWork.setUserName(logWorkRequest.getEmployeeCode());
            listLogWork.setFullName(logWorkRequest.getEmployeeName());
            listLogWork.setDataReceived(data_received);
            listLogWork.setLogWorkType(logWorkRequest.getType());
            listLogWork.setNote(logWorkRequest.getMessage());
            listLogWorkRepository.save(listLogWork);
        }
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
    public static Date convertDateTime(long time) {

        return new Date(time);
    }
    public static double calculateDuration(Date checkIn, Date checkOut) {
        double durationMili = checkOut.getTime() - checkIn.getTime();
        double hours = durationMili / (1000 * 60 * 60);
        return Math.round(hours * 100.0) / 100.0;
    }
}

