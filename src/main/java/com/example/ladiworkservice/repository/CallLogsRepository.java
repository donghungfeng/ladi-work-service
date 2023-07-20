package com.example.ladiworkservice.repository;
import com.example.ladiworkservice.dto.StatisticCallLogDto;
import com.example.ladiworkservice.model.CallLogs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CallLogsRepository extends BaseRepository<CallLogs> {
    @Query(value = "Select * from call_logs cl ORDER BY calldate DESC",nativeQuery = true)
    List<CallLogs> statisticCallLogsByCallDate();
    @Query(nativeQuery = true,name = "CallLogs.statisticCallLogs")
    List<StatisticCallLogDto> statisticCallLogs(@Param("fromCallDate") Long fromCallDate,@Param("toCallDate") Long toCallDate);


}
