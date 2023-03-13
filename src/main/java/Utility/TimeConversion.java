package Utility;

/**
 * @author Patrick Kell
 */

import java.sql.Timestamp;
import java.time.*;


public class TimeConversion {

    // UTC - stored in database
    // EST - appointment times
    //

    public static LocalDateTime localToEST(LocalDateTime ldt) {
        // received from user, convert to est to verify time selection is within working hours
        // time selection files = add,modify appointments
        ZoneId zoneId = ZoneId.systemDefault(); // users (local) zone id
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId); // ZonedDateTime of users (local) zone
        ZonedDateTime est = zdt.withZoneSameInstant(ZoneId.of("America/New_York")); // ZonedDateTime of local zone converted to ETC

        return est.toLocalDateTime();
    }

    public static Timestamp localToUTC(LocalDateTime ldt) {
        // received from the user, convert to utc for database entry
        ZoneId zoneId = ZoneId.systemDefault(); // users (local) zone id
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId); // ZonedDateTime of users (local) zone
        ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC")); // ZonedDateTime of local zone converted to UTC

        return Timestamp.valueOf(utc.toLocalDateTime());
    }

    public static LocalDateTime utcToLocal(Timestamp utcTime) {
        // coming out of the database to the user
        LocalDateTime utc = utcTime.toLocalDateTime();
        ZoneId zoneId = ZoneId.systemDefault(); // users (local) zone id
        ZonedDateTime local = ZonedDateTime.of(utc, zoneId);

        return local.toLocalDateTime();
    }


}
