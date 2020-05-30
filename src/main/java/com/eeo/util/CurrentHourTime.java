package com.eeo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//计算当日凌晨00:00:00unix秒数
public class CurrentHourTime {
    public static long getZeroTime(){
        SimpleDateFormat dateTime11 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat date11 = new SimpleDateFormat("yyyy-MM-dd");
        String format11 = date11.format(new Date());
        Date startTime = null;
        try {
            startTime = dateTime11.parse(format11 + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long times = startTime.getTime();
        return times/1000;
    }
}
