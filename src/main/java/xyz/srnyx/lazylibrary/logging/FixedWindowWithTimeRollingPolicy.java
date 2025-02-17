package xyz.srnyx.lazylibrary.logging;

import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


// https://stackoverflow.com/a/72377192/18122334
public class FixedWindowWithTimeRollingPolicy extends FixedWindowRollingPolicy {
    @NotNull private static final ZoneId ZONE_ID = ZoneId.of("America/New_York");
    @NotNull private static final Pattern DATE_PATTERN = Pattern.compile("%d\\{([^}]+)}");

    @Override
    public void setFileNamePattern(String fnp) {
        // Replace %d{FORMAT} with DateTimeFormatter.ofPattern(FORMAT)
        final OffsetDateTime now = OffsetDateTime.now(ZONE_ID);
        super.setFileNamePattern(DATE_PATTERN.matcher(fnp).replaceAll(m -> DateTimeFormatter.ofPattern(m.group(1)).format(now)));
    }
}
