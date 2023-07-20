package com.example.ladiworkservice.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CallLogsReuqest {
    private String callid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date calldate;
    private String extension;
    private String phone;
    private String duration ;
    private String billsec;
    private String status;
    private String recording;
    private String blacklist;
}
