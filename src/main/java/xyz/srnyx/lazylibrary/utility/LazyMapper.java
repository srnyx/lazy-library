package xyz.srnyx.lazylibrary.utility;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.utils.MiscUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.javautilities.MiscUtility;

import java.util.Optional;


/**
 * A utility class for converting {@link Object Objects} to other types
 */
public class LazyMapper {
    /**
     * Safely parses a {@link String} to a {@link Long snowflake}
     *
     * @param   object  the {@link String} to parse
     *
     * @return          the {@link Long snowflake} or empty
     */
    @NotNull
    public static Optional<Long> parseSnowflake(@NotNull Object object) {
        return MiscUtility.handleException(() -> MiscUtil.parseSnowflake(object.toString()));
    }

    /**
     * Converts an {@link Object} to a {@link UserSnowflake}
     *
     * @param   object  the {@link Object} to convert
     *
     * @return          the {@link UserSnowflake} or empty
     */
    @NotNull
    public static Optional<UserSnowflake> toUserSnowflake(@Nullable Object object) {
        return object == null ? Optional.empty() : parseSnowflake(object).flatMap(snowflake -> MiscUtility.handleException(() -> UserSnowflake.fromId(snowflake)));
    }

    private LazyMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
