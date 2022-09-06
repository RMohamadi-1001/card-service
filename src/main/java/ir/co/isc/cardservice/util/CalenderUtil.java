package ir.co.isc.cardservice.util;

import java.util.Calendar;
import java.util.Date;

public class CalenderUtil {
    private static final String SLASH = "/";
    public static final String FORMAT_DATE = "yyyy/MM/dd";
    public static final String FORMAT_LOCALE = "fa_IR@calendar=persian";

    public static Date addYear(Date date, int years) {
        return add(date, Calendar.YEAR, years);
    }

    private static Date add(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

}
