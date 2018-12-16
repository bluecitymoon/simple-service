package com.pure.service.service.util;

import com.pure.service.service.dto.dto.WeekElement;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static ZoneId defaultShanghaiZoneId = ZoneId.of("Asia/Shanghai");

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

    public static Instant getBeginningOfInstant(Instant instant) {

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
        return localDateTime.with(LocalTime.MIN).toInstant(ZoneOffset.UTC);
    }

    public static Instant getEndingOfInstant(Instant instant) {

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
        return localDateTime.with(LocalTime.MAX).toInstant(ZoneOffset.UTC);
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

    public static Instant getLastSecondOfMonth(LocalDateTime localDateTime) {

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

    public static Instant getFirstSecondOfMonth(LocalDateTime localDateTime) {

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

        LocalDateTime startTime = LocalDateTime.ofInstant(startDate, DateUtil.defaultShanghaiZoneId);
        LocalDateTime endTime = LocalDateTime.ofInstant(endDate, DateUtil.defaultShanghaiZoneId);

        LocalDateTime nextCountDay = startTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(count)));
        String[] hourMinutes = startTimeStr.split(":");

        nextCountDay = nextCountDay.withHour(Integer.valueOf(hourMinutes[0])).withMinute(Integer.valueOf(hourMinutes[1]));

        while (nextCountDay.isBefore(endTime)) {

            Instant nextCountDayInstant = nextCountDay.toInstant(ZoneOffset.ofHours(8));
            countDays.add(nextCountDayInstant);

            System.out.println(nextCountDayInstant.toString());

            nextCountDay = nextCountDay.plusWeeks(1);
        }

        return countDays;
    }

    private static Date getMondayOfWeek() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        int todayCount = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - todayCount);

        return calendar.getTime();
    }

    public static List<WeekElement> getWeekElementsBetween(Instant start, Instant end) {

        List<WeekElement> result = new ArrayList<>();

        Instant nextStartDate = DateUtil.getBeginningOfInstant(start);
        Instant nextEndDate = DateUtil.getEndingOfInstant(start.plus(7, ChronoUnit.DAYS));
        int weekCount = 0;
        while ( nextEndDate.isBefore(end) ) {

            weekCount ++;

            WeekElement weekElement = new WeekElement();
            weekElement.setStart(nextStartDate);
            weekElement.setEnd(nextEndDate);
            weekElement.setWeekIndex(weekCount);
            nextStartDate = Instant.ofEpochSecond(nextEndDate.getEpochSecond());

            nextEndDate = nextEndDate.plus(7, ChronoUnit.DAYS);

            result.add(weekElement);
        }

        WeekElement weekElement = new WeekElement();
        weekElement.setStart(nextStartDate.plus(1, ChronoUnit.SECONDS));
        weekElement.setEnd(DateUtil.getEndingOfInstant(end));
        weekElement.setWeekIndex(weekCount + 1);

        result.add(weekElement);

        System.out.println(result);

        return result;
    }


    public static Instant getCurrentMondayStartSecond() {

        Date monday = getMondayOfWeek();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String mondayDateString = simpleDateFormat.format(monday);

        String fullString = mondayDateString + "T00:00:00.00Z";

        return Instant.parse(fullString);
    }

    public static LocalDateTime instantToLocalDateTime(Instant instant) {

        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    public static Instant getInstantWithSpecialHourMinutes(Instant instant, String hour, String minutes) {

        LocalDateTime localDateTime = instantToLocalDateTime(instant);

        localDateTime = localDateTime.withHour(Integer.valueOf(hour)).withMinute(Integer.valueOf(minutes));

        return localDateTime.toInstant(ZoneOffset.ofHours(8));
    }

    public static boolean isSameday(Instant first, Instant second) {

        LocalDate firstDate = LocalDateTime.ofInstant(first, ZoneId.systemDefault()).toLocalDate();
        LocalDate secondDate = LocalDateTime.ofInstant(second, ZoneId.systemDefault()).toLocalDate();

        return firstDate.equals(secondDate);
    }

//    public static void main(String[] args) {
//
//        Instant start = Instant.parse("2018-11-03T10:15:30.00Z");
//        Instant end = Instant.now();
//
//        getWeekElementsBetween(start, end);
//
//    }
}
