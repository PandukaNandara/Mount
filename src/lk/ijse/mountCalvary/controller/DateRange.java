package lk.ijse.mountCalvary.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class DateRange {

    public static final int ALL  = 0;
    public static final int IN_THIS_WEEK  = 1;
    public static final int IN_THIS_MONTH = 2;
    public static final int IN_THIS_YEAR  = 3;
    private static LocalDate today = LocalDate.now();

    public static String[] getDateRange() {
        return new String[]{
                "All",
                "This week",
                "This month",
                "This year"
        };
    }

    public static boolean checkInRange(Range range, Date date) {
        switch (range) {
            case WEEK:
                return isInThisWeek(date);

            case MONTH:
                return isInThisMonth(date);

            case YEAR:
                return isInThisYear(date);
            case ALL:
                return true;
            default:
                return false;
        }
    }

    private static boolean isInThisMonth(Date dt) {
        LocalDate date = Common.DateToLocalDate(dt);
        if (today.getMonth() == date.getMonth() && isInThisYear(dt))
            return true;
        else
            return false;
    }

    private static boolean isInThisWeek(Date dt) {
        LocalDate date = Common.DateToLocalDate(dt);
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int week_today = today.get(woy);
        int week_day = date.get(woy);
        if (week_day == week_today && isInThisMonth(dt))
            return true;
        return false;
    }

    private static boolean isInThisYear(Date dt) {
        LocalDate date = Common.DateToLocalDate(dt);
        if (date.getYear() == today.getYear())
            return true;
        else
            return false;
    }

    public enum Range {
        WEEK, MONTH, YEAR, ALL

    }

}
