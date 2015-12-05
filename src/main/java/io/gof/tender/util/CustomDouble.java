package io.gof.tender.util;

import java.text.ParseException;
import java.util.Date;

public class CustomDouble {
    public static Double toDouble(String value) {
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toString(Date value) {
        return value.toString();
    }
}
