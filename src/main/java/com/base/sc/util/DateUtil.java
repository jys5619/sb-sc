package com.base.sc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getDatetimeFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
