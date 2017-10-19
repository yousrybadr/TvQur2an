package com.pentavalue.tvquran.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mahmoud Turki
 */
public class DateUtils {

    private static Date convertToDate(String dateStr) {
        String pattern = "yyyy/MM/dd";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateStr(String dateValue) {
        if (ValidatorUtils.isEmpty(dateValue))
            return ".......";
        return convertToDate(dateValue).toString();
    }

    public static String truncateTime(String dateStr) {
        return dateStr.substring(0, dateStr.lastIndexOf('T'));
    }
}
