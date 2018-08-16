package com.pure.service.service.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static String getSimpleToday() {

        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getSimpleTodayInstant() {

        //2007-12-03T10:15:30.00Z
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static Instant getSimpleTodayInstantBegin() {

        //2007-12-03T10:15:30.00Z
        String shortToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fullToday = shortToday + "T00:00:00.00Z";

        return Instant.parse(fullToday);
    }

    public static Instant getSimpleTodayInstantEnd() {

        //2007-12-03T10:15:30.00Z
        String shortToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fullToday = shortToday + "T23:59:59.00Z";

        return Instant.parse(fullToday);
    }

    public static Instant getLastSecondOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.now();

        int year = localDateTime.getYear(), month = localDateTime.getMonthValue();
        int lastDayOfMonth = getLastDayOfMonth(year, month);

        String mouthString = "" + month;
        if (month < 10) {
            mouthString = "0" + month;
        }
        String lastSecond = "" + year + "-" + mouthString + "-" + lastDayOfMonth + "T23:59:59.00Z";

        return Instant.parse(lastSecond);
    }

    public static Instant getFirstSecondOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.now();

        int year = localDateTime.getYear(), month = localDateTime.getMonthValue();

        String mouthString = "" + month;
        if (month < 10) {
            mouthString = "0" + month;
        }
        String firstSecond = "" + year + "-" + mouthString + "-" + "01T00:00:01.00Z";

        return Instant.parse(firstSecond);
    }

    public static int getLastDayOfMonth(int year, int month) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    public static Instant getEveryWeekday(int year, int month, int week, int count) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.WEEK_OF_MONTH, week);
        calendar.set(Calendar.DAY_OF_WEEK, count);

        return calendar.toInstant();
    }

   public static List<Instant> getCountWeekdayInRange(Instant startDate, Instant endDate, int count, String startTimeStr) {

        List<Instant> countDays = new ArrayList<>();

        LocalDateTime startTime = LocalDateTime.ofInstant(startDate, ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endDate, ZoneId.systemDefault());

        LocalDateTime nextCountDay = startTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(count)));
        String[] hourMinutes = startTimeStr.split(":");
        nextCountDay.withHour(Integer.valueOf(hourMinutes[0]) + 1);
        nextCountDay.withMinute(Integer.valueOf(hourMinutes[1]));

        while (nextCountDay.isBefore(endTime)) {

            countDays.add(Instant.ofEpochSecond(nextCountDay.toEpochSecond(ZoneOffset.UTC)));

            nextCountDay = nextCountDay.plusWeeks(1);
        }

        return countDays;
   }
//
//    public static void main(String[] args) {
//
//    }
}
