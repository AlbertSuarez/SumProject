package pereberge.sumproject.utils;

import android.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DateUtils {

    public final static HashMap<Integer, Pair<Integer, Integer> > timeZoneCodes;
    static {
        timeZoneCodes = new HashMap<>();
        timeZoneCodes.put(0, new Pair<>(11, 0));
        timeZoneCodes.put(1, new Pair<>(12, 30));
        timeZoneCodes.put(2, new Pair<>(14, 0));
        timeZoneCodes.put(3, new Pair<>(15, 30));
        timeZoneCodes.put(4, new Pair<>(17, 0));
        timeZoneCodes.put(5, new Pair<>(18, 30));
        timeZoneCodes.put(6, new Pair<>(20, 0));
        timeZoneCodes.put(7, new Pair<>(21, 30));
    }

    public static Boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return  calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    public static Boolean isPassed(Date date){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        return calendar2.get(Calendar.HOUR_OF_DAY) <= calendar1.get(Calendar.HOUR_OF_DAY);
    }

    public static Boolean isSameHour(Date date, Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar.get(Calendar.HOUR_OF_DAY) == hour
                && calendar.get(Calendar.MINUTE) == minute;
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getToday());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date createDate(Integer day, Integer month, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date createDateOfToday(Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getToday());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date createDateOfTomorrow(Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getTomorrow());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date millisToDate(Long millis) {
        return new Date(millis);
    }

    public static String dayToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    public static String hourToString(Date date) {
        String ret = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        Integer minute = calendar.get(Calendar.MINUTE);

        if (hour < 10) ret += "0";
        ret += hour.toString() + ":";
        if (minute < 10) ret += "0";
        ret += minute.toString();

        ret += " - ";
        calendar.add(Calendar.MINUTE, 90);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        if (hour < 10) ret += "0";
        ret += hour.toString() + ":";
        if (minute < 10) ret += "0";
        ret += minute.toString();

        return ret;
    }

    public static Integer getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Date nextTimeZone(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 90);
        return calendar.getTime();
    }

}
