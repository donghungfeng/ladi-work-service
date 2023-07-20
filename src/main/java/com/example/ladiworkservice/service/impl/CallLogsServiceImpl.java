package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.dto.StatisticCallLogDto;
import com.example.ladiworkservice.model.CallLogs;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.CallLogsRepository;
import com.example.ladiworkservice.service.CallLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallLogsServiceImpl extends BaseServiceImpl<CallLogs> implements CallLogsService {
    @Autowired
    CallLogsRepository callLogsRepository;
    @Override
    protected BaseRepository<CallLogs> getRepository() {
        return callLogsRepository;
    }
    @Override
    public BaseResponse statisticCallLogsByCallDate() {
        List<CallLogs> callLogsList=callLogsRepository.statisticCallLogsByCallDate();

        return new BaseResponse(200, "Ok", callLogsList);
    }

    @Override
    public BaseResponse statisticCallLogs(String fromCallDate, String toCallDate){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        List<StatisticCallLogDto> statisticCallLogDto=callLogsRepository.statisticCallLogs(Long.parseLong(fromCallDate),Long.parseLong(toCallDate));
        List<StatisticCallLogDto> resultList=statisticCallLogDto.stream().map((item)->{
            item.setPercent_ANSWERED(Float.valueOf(df.format(item.getPercent_ANSWERED())));
            item.setPercent_NOANSWER(Float.valueOf(df.format(item.getPercent_NOANSWER())));
            item.setPercent_BUSY(Float.valueOf(df.format(item.getPercent_BUSY())));
            item.setPercent_CONGESTION(Float.valueOf(df.format(item.getPercent_CONGESTION())));
            item.setAverageTime(Float.valueOf(df.format(item.getAverageTime())));

            return item;
        }).collect(Collectors.toList());
        return new BaseResponse(200,"OK",resultList);
    }

    @Override
    public  BaseResponse getAllCallLogsByDuration(String sortDirection){

        Sort sort = Sort.by("duration");
        if (sortDirection.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        List<CallLogs> callLogsList= callLogsRepository.findAll(sort);

        return new BaseResponse(200,"ok",callLogsList);
    }

}
