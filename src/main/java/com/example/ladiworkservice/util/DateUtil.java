package com.example.ladiworkservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public static Long getDate(Long dateTime) {
        return dateTime / 1000000;
    }

    public static Long getCurrenDate() {
        return Long.parseLong(dateFormat.format(new Date()));
    }
}
