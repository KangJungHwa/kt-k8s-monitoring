package com.kt.monitoring.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class TimeUtil {

    public static java.sql.Timestamp getNow() {
        Instant instant = Instant.now();
        Timestamp now = Timestamp.from(instant);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        return Timestamp.valueOf(df.format(now));
    }
}
