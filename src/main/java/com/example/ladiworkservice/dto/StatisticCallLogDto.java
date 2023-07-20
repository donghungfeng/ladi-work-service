package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticCallLogDto {
    private int totalCallLogs;
    private int noAnswerCallLogs;
    private int answeredCallLogs;
    private int busyCallLogs;
    private int congestionCallLogs;
    private Float percent_NOANSWER;
    private Float percent_ANSWERED;
    private Float percent_BUSY;
    private Float percent_CONGESTION;
    private Float averageTime;
    private int numberOfBlacklist;
}
