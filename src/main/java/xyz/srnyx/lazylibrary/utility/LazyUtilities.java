package xyz.srnyx.lazylibrary.utility;

import com.freya02.botcommands.api.application.slash.autocomplete.AutocompleteAlgorithms;
import com.freya02.botcommands.api.pagination.paginator.PaginatorBuilder;

import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.MiscUtility;
import xyz.srnyx.javautilities.manipulation.Mapper;

import xyz.srnyx.lazylibrary.LazyEmoji;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;


/**
 * Class for miscellaneous utilities
 */
public class LazyUtilities {
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
     * Checks if the executing {@link User user} has the given {@link Permission permissions} in the executing {@link GuildChannel channel}
     *
     * @param   interaction the {@link Interaction} to get the {@link Member member} and {@link GuildChannel channel} from
     * @param   permissions the {@link Permission permissions} to check for
     *
     * @return              true if the {@link Member member} has the {@link Permission permissions} in the {@link GuildChannel channel}, false if not or if the {@link Member member}/{@link GuildChannel channel} is null (not executed in a {@link GuildChannel})
     */
    public static boolean userHasChannelPermission(@NotNull Interaction interaction, @NotNull Permission... permissions) {
        final Member member = interaction.getMember();
        final GuildChannel channel = MiscUtility.handleException(() -> (GuildChannel) interaction.getChannel());
        if (member == null || channel == null) return false;
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
        final Function<BoundExtractedResult<Command.Choice>, Command.Choice> mapping = switch (type) {
            case STRING -> BoundExtractedResult::getReferent;
            case INTEGER -> result -> {
                final Command.Choice choice = result.getReferent();
                final Long value = Mapper.toLong(choice.getAsString());
                return value == null ? null : new Command.Choice(choice.getName(), value);
            };
            case NUMBER -> result -> {
                final Command.Choice choice = result.getReferent();
                final Double value = Mapper.toDouble(choice.getAsString());
                return value == null ? null : new Command.Choice(choice.getName(), value);
            };
            default -> throw new IllegalArgumentException("Invalid autocompletion option type: " + type);
        };
        return AutocompleteAlgorithms.fuzzyMatching(collection, Command.Choice::getName, event).stream()
                .map(mapping)
                .toList();
    }

    private LazyUtilities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
