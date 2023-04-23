package xyz.srnyx.lazylibrary.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * A utility class for parsing {@link Duration durations} from strings
 */
public class DurationParser {
    private static final Map<ChronoUnit, String> UNITS_PATTERNS = new EnumMap<>(ChronoUnit.class);

    static {
        UNITS_PATTERNS.put(ChronoUnit.YEARS, "y(?:ear)?s?");
        UNITS_PATTERNS.put(ChronoUnit.MONTHS, "mo(?:nth)?s?");
        UNITS_PATTERNS.put(ChronoUnit.WEEKS, "w(?:eek)?s?");
        UNITS_PATTERNS.put(ChronoUnit.DAYS, "d(?:ay)?s?");
        UNITS_PATTERNS.put(ChronoUnit.HOURS, "h(?:our|r)?s?");
        UNITS_PATTERNS.put(ChronoUnit.MINUTES, "m(?:inute|in)?s?");
        UNITS_PATTERNS.put(ChronoUnit.SECONDS, "s(?:econd|ec)?s?");
    }

    private static final Duration[] DURATIONS = UNITS_PATTERNS.keySet().stream()
            .map(ChronoUnit::getDuration)
            .toArray(Duration[]::new);

    private static final Pattern PATTERN = Pattern.compile(UNITS_PATTERNS.values().stream()
            .map(pattern -> "(?:(\\d+)\\s*" + pattern + "[,\\s]*)?")
            .collect(Collectors.joining("", "^\\s*", "$")), Pattern.CASE_INSENSITIVE);

    /**
     * Parses a {@link Duration} from a string
     *
     * @param   input   the string to parse
     *
     * @return          the parsed duration, or {@code null} if the string is invalid
     */
    @Nullable
    public static Duration parse(@NotNull String input) {
        final Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) return null;

        Duration duration = Duration.ZERO;
        for (int i = 0; i < DURATIONS.length; i++) {
            final String group = matcher.group(i + 1);
            if (group != null && !group.isEmpty()) {
                final Integer number = LazyMapper.toInt(group);
                if (number != null) duration = duration.plus(DURATIONS[i].multipliedBy(number));
            }
        }

        return duration;
    }

    private DurationParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
