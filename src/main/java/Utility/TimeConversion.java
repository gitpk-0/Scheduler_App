package Utility;

/**
 * @author Patrick Kell
 */

import java.sql.Timestamp;
import java.time.*;


public class TimeConversion {

    public static LocalDateTime localToEST(LocalDateTime ldt) {
        // received from user, convert to est to verify time selection is within working hours

        // Convert user's LocalDateTime to ZonedDateTime
        ZonedDateTime localZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        // Converts localZdt to UTC (toInstant), then converts UTC to EST (ofInstant)
        ZonedDateTime est = ZonedDateTime.ofInstant(localZdt.toInstant(), ZoneId.of("America/New_York")); // ZonedDateTime of local zone converted to ETC

        return est.toLocalDateTime();
    }

    public static Timestamp localToUTC(LocalDateTime ldt) {
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault()); // ZonedDateTime of users local zone
        ZonedDateTime utc = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.of("UTC"));

        return Timestamp.valueOf(utc.toLocalDateTime());
    }

    public static LocalDateTime utcToLocal(Timestamp utcTime) {
        // Convert the UTC time received from the database into the user's local time
        LocalDateTime utcT = utcTime.toLocalDateTime(); // UTC local date time
        ZonedDateTime utcZdt = ZonedDateTime.of(utcT, ZoneId.of("UTC")); // UTC zoned date time
        ZonedDateTime localZdt = ZonedDateTime.ofInstant(utcZdt.toInstant(), ZoneId.systemDefault());

        return localZdt.toLocalDateTime();
    }


}
