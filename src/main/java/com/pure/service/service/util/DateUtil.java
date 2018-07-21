package com.pure.service.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getSimpleToday() {

        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getSimpleTodayInstant() {

        //2007-12-03T10:15:30.00Z
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
