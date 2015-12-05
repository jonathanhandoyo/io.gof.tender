package io.gof.tender.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
    public static Date toDate(String pattern, String value) {
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toString(String pattern, Date value) {
        return new SimpleDateFormat(pattern).format(value);
    }
}
