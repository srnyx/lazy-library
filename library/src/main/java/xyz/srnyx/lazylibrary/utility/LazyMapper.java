package xyz.srnyx.lazylibrary.utility;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.utils.MiscUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A utility class for converting {@link Object Objects} to other types
 */
public class LazyMapper {
    /**
     * Converts an {@link Object} to the specified {@link Class}
     *
     * @param   object  the {@link Object} to convert
     * @param   clazz   the {@link Class} to convert to
     *
     * @return          the converted {@link Object} or {@code null}
     *
     * @param   <T>     the type of the {@link Class}
     */
    @Nullable
    public static <T> T to(@Nullable Object object, @NotNull Class<T> clazz) {
        if (!clazz.isInstance(object)) return null;
        return LazyUtilities.handleException(() -> clazz.cast(object));
    }

    /**
     * Converts an {@link Object} to a {@link Integer}
     *
     * @param   object  the {@link Object} to convert
     *
     * @return          the {@link Integer} or {@code null}
     */
    @Nullable
    public static Integer toInt(@Nullable Object object) {
        if (object == null) return null;
        return LazyUtilities.handleException(() -> Integer.parseInt(object.toString()));
    }

    /**
     * Converts an {@link Object} to a {@link Double}
     *
     * @param   object  the {@link Object} to convert
     *
     * @return          the {@link Double} or {@code null}
     */
    @Nullable
    public static Double toDouble(@Nullable Object object) {
        if (object == null) return null;
        return LazyUtilities.handleException(() -> Double.parseDouble(object.toString()));
    }

    /**
     * Converts an {@link Object} to a {@link Long}
     *
     * @param   object  the {@link Object} to convert
     *
     * @return          the {@link Long} or {@code null}
     */
    @Nullable
    public static Long toLong(@Nullable Object object) {
        if (object == null) return null;
        return LazyUtilities.handleException(() -> Long.parseLong(object.toString()));
    }

    /**
     * Safely parses a {@link String} to a {@link Long snowflake}
     *
     * @param   object  the {@link String} to parse
     *
     * @return          the {@link Long snowflake} or {@code null}
     */
    @Nullable
    public static Long parseSnowflake(@NotNull Object object) {
        return LazyUtilities.handleException(() -> MiscUtil.parseSnowflake(object.toString()));
    }

    /**
     * Converts an {@link Object} to a {@link UserSnowflake}
     *
     * @param   object  the {@link Object} to convert
     *
     * @return          the {@link UserSnowflake} or {@code null}
     */
    @Nullable
    public static UserSnowflake toUserSnowflake(@Nullable Object object) {
        if (object == null) return null;
        final Long snowflake = parseSnowflake(object);
        if (snowflake == null) return null;
        return LazyUtilities.handleException(() -> UserSnowflake.fromId(snowflake));
    }

    private LazyMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
