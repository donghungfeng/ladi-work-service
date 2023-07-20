package com.example.ladiworkservice.model;

import com.example.ladiworkservice.dto.StatisticCallLogDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;

@NamedNativeQuery(name = "CallLogs.statisticCallLogs",
        query = "SELECT \n" +
                "(SELECT COUNT(*) FROM call_logs cl WHERE cl.calldate BETWEEN :fromCallDate AND :toCallDate ) as totalCallLogs,\n" +
                "COALESCE(SUM(CASE WHEN status = 'NO ANSWER' THEN 1 ELSE 0 END),0) as NoAnswerCallLogs,\n" +
                "COALESCE(SUM(CASE WHEN status = 'ANSWERED' THEN 1 ELSE 0 END),0) as answeredCallLogs,\n" +
                "COALESCE(SUM(CASE WHEN status = 'BUSY' THEN 1 ELSE 0 END),0) as busyCallLogs,\n" +
                "COALESCE(SUM(CASE WHEN status = 'CONGESTION' THEN 1 ELSE 0 END),0) as congestionCallLogs,\n" +
                "COALESCE((SUM(CASE WHEN status = 'NO ANSWER' THEN 1 ELSE 0 END)*1.0/COUNT(*)*100),0) AS percent_NOANSWER,\n" +
                "COALESCE((SUM(CASE WHEN status = 'ANSWERED' THEN 1 ELSE 0 END)*1.0/COUNT(*)*100),0) AS percent_ANSWERED,\n" +
                "COALESCE((SUM(CASE WHEN status = 'BUSY' THEN 1 ELSE 0 END)*1.0/COUNT(*)*100),0) AS percent_BUSY,\n" +
                "COALESCE((SUM(CASE WHEN status = 'CONGESTION' THEN 1 ELSE 0 END)*1.0/COUNT(*)*100),0) AS percent_CONGESTION,\n" +
                "COALESCE((SELECT sum(duration) FROM call_logs cl2 WHERE cl2.status ='ANSWERED' AND cl2.calldate BETWEEN :fromCallDate AND :toCallDate )/(SUM(CASE WHEN status = 'ANSWERED' THEN 1 ELSE 0 END)),0) AS averageTime,\n" +
                "(SELECT COUNT(DISTINCT phone) FROM call_logs cl WHERE cl.blacklist ='DNC' AND cl.calldate BETWEEN :fromCallDate AND :toCallDate ) AS numberOfBlacklist\n" +
                "FROM call_logs cl \n" +
                "WHERE cl.calldate BETWEEN :fromCallDate AND :toCallDate ;",
        resultSetMapping = "Mapping.StatisticCallLogDto")
@SqlResultSetMapping(name = "Mapping.StatisticCallLogDto", classes = @ConstructorResult(targetClass = StatisticCallLogDto.class,
        columns = {@ColumnResult(name = "totalCallLogs", type = int.class),
                @ColumnResult(name = "noAnswerCallLogs", type = int.class),
                @ColumnResult(name = "answeredCallLogs", type = int.class),
                @ColumnResult(name = "busyCallLogs", type = int.class),
                @ColumnResult(name = "congestionCallLogs", type = int.class),
                @ColumnResult(name = "percent_NOANSWER", type = Float.class),
                @ColumnResult(name = "percent_ANSWERED", type = Float.class),
                @ColumnResult(name = "percent_BUSY", type = Float.class),
                @ColumnResult(name = "percent_CONGESTION", type = Float.class),
                @ColumnResult(name = "averageTime", type = Float.class),
                @ColumnResult(name = "numberOfBlacklist", type = int.class)
        }))
@Entity
@Table(name = "call_logs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CallLogs extends BaseEntity{
    @Column(name = "callid")
    private String callid;

    @Column(name = "calldate")
    @JsonFormat(pattern="yyyyMMddHHMMss")
    private Long calldate;

    @Column(name = "extension")
    private String extension;

    @Column(name = "phone")
    private String phone;

    @Column(name = "duration")
    private String duration ;

    @Column(name = "billsec")
    private String billsec;

    @Column(name = "status")
    private String status;

    @Column(name = "recording")
    private String recording;

    @Column(name = "blacklist")
    private String blacklist;
}
