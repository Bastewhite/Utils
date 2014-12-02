package commons;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsDate {

    public static String getStringFromDate(Date date) {
        return getStringFromDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringFromDate(Date date, String format) {
        if (date == null)
            return "";

        Format formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date getDateFromString(String stDate) {
        return getDateFromString(stDate, "yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(String stDate, String format) {
        if (TextUtils.isEmpty(stDate))
            return null;

        try {
            return new SimpleDateFormat(format).parse(stDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
