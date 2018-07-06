package com.pure.service.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getSimpleToday() {

        return new SimpleDateFormat("yyyMMdd").format(new Date());
    }
}
