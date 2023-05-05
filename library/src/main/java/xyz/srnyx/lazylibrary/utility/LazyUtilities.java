package xyz.srnyx.lazylibrary.utility;

import com.freya02.botcommands.api.application.slash.autocomplete.AutocompleteAlgorithms;
import com.freya02.botcommands.api.pagination.paginator.PaginatorBuilder;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.lazylibrary.LazyEmoji;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * Class for miscellaneous utilities
 */
public class LazyUtilities {
    /**
     * If an exception is thrown by the {@link Supplier}, {@code null} is returned
     *
     * @param   supplier    the {@link Supplier} to execute
     *
     * @return              the result of the {@link Supplier} or {@code null}
     *
     * @param   <R>         the type of the result
     */
    @Nullable
    public static <R> R handleException(@NotNull Supplier<R> supplier) {
        return handleException(supplier, null);
    }

    /**
     * If an exception is thrown by the {@link Supplier}, the default value is returned
     *
     * @param   supplier    the {@link Supplier} to execute
     * @param   def         the default value to return if an exception is thrown
     *
     * @return              the result of the {@link Supplier} or the default value
     *
     * @param   <R>         the type of the result
     */
    @Nullable
    public static <R> R handleException(@NotNull Supplier<R> supplier, @Nullable R def) {
        try {
            return supplier.get();
        } catch (final Exception e) {
            return def;
        }
    }

    /**
     * Shortens a string to a given length, adding "..." at the end ("..." is included in the length)
     *
     * @param   string  the string to shorten
     * @param   length  the length to shorten to
     *
     * @return          the shortened string
     */
    @NotNull
    public static String shorten(@NotNull String string, int length) {
        return string.length() + 3 > length ? string.substring(0, length - 3) + "..." : string;
    }

    /**
     * Get a {@link PaginatorBuilder} with {@link LazyEmoji#LEFT_CLEAR_DARK} and {@link LazyEmoji#RIGHT_CLEAR_DARK} as the previous and next buttons respectively
     *
     * @return  the {@link PaginatorBuilder}
     */
    @NotNull
    public static PaginatorBuilder getDefaultPaginator() {
        return new PaginatorBuilder()
                .setPreviousContent(LazyEmoji.LEFT_CLEAR_DARK.getButtonContent(null))
                .setNextContent(LazyEmoji.RIGHT_CLEAR_DARK.getButtonContent(null));
    }

    /**
     * Gets the names of all YML files in a given folder
     *
     * @param   path    the path to the folder
     *
     * @return          the names of all YML files in the folder
     */
    @NotNull
    public static Set<String> getYmlFileNames(@NotNull String path) {
        final File[] files = new File(path).listFiles();
        if (files == null) return new HashSet<>();
        return Set.of(files).stream()
                .map(File::getName)
                .map(name -> name.replace(".yml", ""))
                .collect(Collectors.toSet());
    }

    /**
     * Deletes a file
     *
     * @param   path        the path to the file
     * @param   silentFail  if true, the error will be printed if the task fails
     */
    public static void deleteFile(@NotNull Path path, boolean silentFail) {
        try {
            Files.delete(path);
        } catch (final IOException e) {
            if (!silentFail) e.printStackTrace();
        }
    }

    /**
     * Checks if the executing {@link User user} has the given {@link Permission permissions} in the executing {@link GuildChannel channel}
     *
     * @param   interaction the {@link Interaction} to get the {@link Member member} and {@link GuildChannel channel} from
     * @param   permissions the {@link Permission permissions} to check for
     *
     * @return              true if the {@link Member member} has the {@link Permission permissions} in the {@link GuildChannel channel}, false if not, or null if the {@link Member member} or {@link GuildChannel channel} is null (not executed in a {@link Guild} and/or {@link GuildChannel})
     */
    @Nullable
    public static Boolean userHasChannelPermission(@NotNull Interaction interaction, @NotNull Permission... permissions) {
        final Member member = interaction.getMember();
        final GuildChannel channel = handleException(() -> (GuildChannel) interaction.getChannel());
        if (member == null || channel == null) return null;
        return member.hasPermission(channel, permissions);
    }

    /**
     * Sorts {@link Command.Choice command choices} using fuzzy matching based on the focused option (most similar)
     *
     * @param   event       the event to get the focused option from
     * @param   collection  the collection of choices to sort
     *
     * @return              the sorted choices
     */
    @NotNull
    public static List<Command.Choice> sortChoicesFuzzy(@NotNull CommandAutoCompleteInteractionEvent event, @NotNull Collection<Command.Choice> collection) {
        final OptionType type = event.getFocusedOption().getType();
        return AutocompleteAlgorithms.fuzzyMatching(collection, Command.Choice::getName, event).stream()
                .map(result -> {
                    final Command.Choice choice = result.getReferent();
                    final String name = choice.getName();
                    final String value = choice.getAsString();
                    return switch (type) {
                        case STRING -> new Command.Choice(name, value);
                        case INTEGER -> {
                            final Long valueLong = LazyMapper.toLong(value);
                            yield valueLong == null ? null : new Command.Choice(name, valueLong);
                        }
                        case NUMBER -> {
                            final Double valueDouble = LazyMapper.toDouble(value);
                            yield valueDouble == null ? null : new Command.Choice(name, valueDouble);
                        }
                        default -> throw new IllegalArgumentException("Invalid autocompletion option type: " + type);
                    };
                })
                .toList();
    }

    private LazyUtilities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
