package com.cine.back.movieList.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String getPreviousSundayDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeekDate = currentDate.minusWeeks(1);
        LocalDate sunday = lastWeekDate.with(DayOfWeek.SUNDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return sunday.format(formatter);
    }

}
