package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class for handling all time conversions and time related things
 */
public class TimeKeeper {

    public static final long ONE_MINUTE_IN_MILLIS = 60000;
    public static final long ONE_WEEK_IN_MILLIS = 604800000;

    public static String dateStringFromUTC(String dateTime) throws ParseException {

        Date date = new Date();

        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.parse(dateTime);

        //date.setTime(date.getTime());

        return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime())).toString();

    }

    public static Date dateFromUTC(String dateTime) throws ParseException {

        Date date = new Date();

        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.parse(dateTime);

        //date.setTime(date.getTime());

        return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime()));

    }

    /**
     * Convert date to UTC
     * @param localDate
     * @param hour
     * @return outputDate
     */
    public static Date dateToUTC(LocalDate localDate, int hour) throws ParseException {

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        date.setHours(hour);

        SimpleDateFormat utcformatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcformatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        String outputDateString = utcformatter.format(date);

        Date outputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(outputDateString);


        return outputDate;

    }

    /**
     * Converts string into date format
     * @return outputDate
     */
    public static Date stringToDate(String dateStr) throws ParseException {

        //SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //utcformatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        //String outputDateString = utcformatter.parse(dateStr);

        Date outputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);


        return outputDate;

    }

    /**
     * Converts date to EST
     * @param localDate
     * @param hour
     * @return outputDate
     */
    public static Date dateToEST(LocalDate localDate, int hour) throws ParseException {

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        date.setHours(hour);

        SimpleDateFormat estformatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        estformatter.setTimeZone(TimeZone.getTimeZone("EST"));

        Date outputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(estformatter.format(date));

        return outputDate;

    }
}
