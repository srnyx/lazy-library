package xyz.srnyx.lazylibrary.logging;

import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


/**
 * A {@link FixedWindowRollingPolicy} that replaces {@code %d{FORMAT}} with {@link DateTimeFormatter#ofPattern(String)}
 * <br>Based on <a href="https://stackoverflow.com/a/72377192/18122334">a StackOverflow answer</a>
 */
public class FixedWindowWithTimeRollingPolicy extends FixedWindowRollingPolicy {
    @NotNull private static final Pattern DATE_PATTERN = Pattern.compile("%d\\{([^}]+)}");

    String zoneId;

    /**
     * Constructs a new {@link FixedWindowWithTimeRollingPolicy} with the default options
     */
    public FixedWindowWithTimeRollingPolicy() {
        super();
        zoneId = "US/Eastern";
    }

    @Override
    public void setFileNamePattern(String fnp) {
        // Replace %d{FORMAT} with DateTimeFormatter.ofPattern(FORMAT)
        final OffsetDateTime now = OffsetDateTime.now(ZoneId.of(zoneId));
        super.setFileNamePattern(DATE_PATTERN.matcher(fnp).replaceAll(m -> DateTimeFormatter.ofPattern(m.group(1)).format(now)));
    }

    /**
     * Sets the {@link ZoneId} to use when formatting the date
     *
     * @param   zoneId  the {@link ZoneId} to use when formatting the date
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * Gets the {@link ZoneId} to use when formatting the date
     *
     * @return  the {@link ZoneId} to use when formatting the date
     */
    public String getZoneId() {
        return zoneId;
    }
}
