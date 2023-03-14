package Utility;

/**
 * @author Patrick Kell
 */

import java.sql.Timestamp;
import java.time.*;


/**
 * TimeConversion utilty class which manages the conversion of times between UTC, EST and the user's local time.
 */
public class TimeConversion {

    /**
     * LocalToEST class which manages the conversion of the user's local time into EST
     * <p>
     * This method is used to help verify the time selection of the appointment is within EST working hours
     *
     * @param ldt Local date time received from the user
     * @return Local date time converted to EST
     */
    public static LocalDateTime localToEST(LocalDateTime ldt) {
        // Convert user's LocalDateTime to a ZonedDateTime object
        ZonedDateTime localZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        // Converts localZdt to UTC (toInstant), then converts UTC to EST (ofInstant)
        ZonedDateTime est = ZonedDateTime.ofInstant(localZdt.toInstant(), ZoneId.of("America/New_York")); // ZonedDateTime of local zone converted to ETC

        return est.toLocalDateTime();
    }

    /**
     * LocalToUTC class which manages the conversion of the user's local time into UTC
     * <p>
     * This method is used to convert all times being inserted into the database into UTC time
     *
     * @param ldt Local date time received from the user
     * @return A Timestamp value of the converted time (in UTC)
     */
    public static Timestamp localToUTC(LocalDateTime ldt) {
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault()); // ZonedDateTime of users local zone
        ZonedDateTime utc = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.of("UTC")); // converted to UTC

        return Timestamp.valueOf(utc.toLocalDateTime()); // converted to a TimeStamp value which is used by the database
    }

    /**
     * UtcToLocal method which manages the conversion of the database (UTC) time to the user's local time
     * <p>
     * This method is used to convert times that are stored in the database into the user's local time
     *
     * @param utcTime Timestamp value of the UTC time stored in the database
     * @return Local date time converted to the user's local time
     */
    public static LocalDateTime utcToLocal(Timestamp utcTime) {
        LocalDateTime utcT = utcTime.toLocalDateTime(); // UTC local date time object
        ZonedDateTime utcZdt = ZonedDateTime.of(utcT, ZoneId.of("UTC")); // UTC zoned date time object
        // Zoned date time object of the converted utc time to the user's local time
        ZonedDateTime localZdt = ZonedDateTime.ofInstant(utcZdt.toInstant(), ZoneId.systemDefault());

        return localZdt.toLocalDateTime();
    }
}