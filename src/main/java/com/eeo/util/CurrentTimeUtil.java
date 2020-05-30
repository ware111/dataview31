package com.eeo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author panhm
 * 时间工具类
 * */
public class CurrentTimeUtil {

    //判断当前时间是否是整点时间
    public static boolean IsIntegralTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        String substring = currentTime.substring(3);
        boolean equals = substring.equals("00:00");
        return equals;
    }


    public static String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }

}
