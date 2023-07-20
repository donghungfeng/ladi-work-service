package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CallLogsReuqest;
import com.example.ladiworkservice.model.CallLogs;
import com.example.ladiworkservice.service.CallLogsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("call-logs")
@CrossOrigin
public class CallLogsController {

    @Autowired
    CallLogsService callLogsService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("statisticCallLogByCallDate")
    public BaseResponse statisticCallLogByCallDate(){
        return callLogsService.statisticCallLogsByCallDate();
    }

    @GetMapping("statisticCallLogs")
    public BaseResponse statisticCallLogs(String fromCallDate,String toCallDate){
        return callLogsService.statisticCallLogs(fromCallDate,toCallDate);
    }
    @GetMapping("statisticCallLogsDuration")
    public BaseResponse findCallLogsByDuration( @RequestParam(value = "sort", defaultValue = "asc") String sortDirection){
        return callLogsService.getAllCallLogsByDuration(sortDirection);
    }

    @PostMapping("/create")
    public BaseResponse create(@RequestBody CallLogsReuqest body) throws NoSuchAlgorithmException, IOException {
        LocalDateTime date = body.getCalldate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        CallLogs callLogs = modelMapper.map(body, CallLogs.class);
        Long callDate = Long.parseLong(date.format(formatter));
        callLogs.setCalldate(callDate);
        return callLogsService.create(callLogs);
    }

    @GetMapping("/search")
    public BaseResponse search(@RequestParam String filter, @RequestParam String sort, @RequestParam int size, @RequestParam int page){
        return callLogsService.search(filter, sort, size, page);
    }
}
