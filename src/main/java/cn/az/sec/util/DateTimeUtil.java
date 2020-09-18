package cn.az.sec.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author azusachino
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
        return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
    }

    public static String dateToStr(LocalDateTime date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return date.format(DateTimeFormatter.ofPattern(formatStr));
    }

    public static LocalDateTime strToDate(String dateTimeStr) {
        return strToDate(dateTimeStr, STANDARD_FORMAT);
    }

    public static String dateToStr(LocalDateTime date) {
        return dateToStr(date, STANDARD_FORMAT);
    }

}
