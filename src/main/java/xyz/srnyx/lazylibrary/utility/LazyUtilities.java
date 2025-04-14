package xyz.srnyx.lazylibrary.utility;

import com.freya02.botcommands.api.pagination.paginator.PaginatorBuilder;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.ToStringFunction;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.interactions.AutoCompleteQuery;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.ErrorResponse;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.manipulation.Mapper;

import xyz.srnyx.lazylibrary.LazyEmoji;

import java.util.*;
import java.util.function.Function;


/**
 * Class for miscellaneous utilities
 */
public class LazyUtilities {
    /**
     * An empty set of {@link Message.MentionType mention types}
     */
    @NotNull public static final Set<Message.MentionType> NO_MENTIONS = Collections.emptySet();
    /**
     * An {@link ErrorHandler} that ignores {@link ErrorResponse#UNKNOWN_MESSAGE} errors
     */
    @NotNull public static final ErrorHandler IGNORE_UNKNOWN_MESSAGE = new ErrorHandler().ignore(ErrorResponse.UNKNOWN_MESSAGE);
    /**
     * An {@link ErrorHandler} that ignores {@link ErrorResponse#CANNOT_SEND_TO_USER} errors
     */
    @NotNull public static final ErrorHandler IGNORE_CANNOT_SEND_TO_USER = new ErrorHandler().ignore(ErrorResponse.CANNOT_SEND_TO_USER);
    /**
     * An {@link ErrorHandler} that ignores {@link ErrorResponse#UNKNOWN_MEMBER} errors
     */
    @NotNull public static final ErrorHandler IGNORE_UNKNOWN_MEMBER = new ErrorHandler().ignore(ErrorResponse.UNKNOWN_MEMBER);
    /**
     * An {@link ErrorHandler} that ignores {@link ErrorResponse#MAX_MESSAGE_PINS} errors
     */
    @NotNull public static final ErrorHandler IGNORE_MAX_MESSAGE_PINS = new ErrorHandler().ignore(ErrorResponse.MAX_MESSAGE_PINS);
    /**
     * An {@link ErrorHandler} that ignores {@link ErrorResponse#MISSING_PERMISSIONS} errors
     */
    @NotNull public static final ErrorHandler IGNORE_MISSING_PERMISSIONS = new ErrorHandler().ignore(ErrorResponse.MISSING_PERMISSIONS);

    /**
     * Get a {@link PaginatorBuilder} with:
     * <ul>
     *     <li>{@link LazyEmoji#BACK_CLEAR_DARK} as the "first page" button</li>
     *     <li>{@link LazyEmoji#LEFT2_CLEAR_DARK} as the "previous page" button</li>
     *     <li>{@link LazyEmoji#RIGHT2_CLEAR_DARK} as the "next page" button</li>
     *     <li>{@link LazyEmoji#FORWARD_CLEAR_DARK} as the "last page" button</li>
     * </ul>
     *
     * @return  the {@link PaginatorBuilder}
     */
    @NotNull
    public static PaginatorBuilder getDefaultPaginator() {
        return new PaginatorBuilder()
                .setFirstContent(LazyEmoji.BACK_CLEAR_DARK.getButtonContent())
                .setPreviousContent(LazyEmoji.LEFT2_CLEAR_DARK.getButtonContent())
                .setNextContent(LazyEmoji.RIGHT2_CLEAR_DARK.getButtonContent())
                .setLastContent(LazyEmoji.FORWARD_CLEAR_DARK.getButtonContent());
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
        return member != null && Mapper.to(interaction.getChannel(), GuildChannel.class)
                .map(channel -> member.hasPermission(channel, permissions))
                .orElse(false);
    }

    @NotNull private static final Function<BoundExtractedResult<Command.Choice>, Command.Choice> STRING_MAPPING = BoundExtractedResult::getReferent;
    @NotNull private static final Function<BoundExtractedResult<Command.Choice>, Command.Choice> INTEGER_MAPPING = result -> {
        final Command.Choice choice = result.getReferent();
        return Mapper.toLong(choice.getAsString())
                .map(value -> new Command.Choice(choice.getName(), value))
                .orElse(null);
    };
    @NotNull private static final Function<BoundExtractedResult<Command.Choice>, Command.Choice> NUMBER_MAPPING = result -> {
        final Command.Choice choice = result.getReferent();
        return Mapper.toDouble(choice.getAsString())
                .map(value -> new Command.Choice(choice.getName(), value))
                .orElse(null);
    };

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
        final AutoCompleteQuery inputQuery = event.getFocusedOption();
        final OptionType type = inputQuery.getType();
		final String input = inputQuery.getValue().toLowerCase();
        final ToStringFunction<Command.Choice> toStringFunction = choice -> choice.getName().toLowerCase();

		// Sort results by similarities but taking into account an incomplete input
		final List<Command.Choice> list = collection.stream()
				.sorted(Comparator.comparing(toStringFunction::apply))
				.toList();
		final List<Command.Choice> bigLengthDiffResults = FuzzySearch.extractTop(input, list, toStringFunction, FuzzySearch::partialRatio, OptionData.MAX_CHOICES).stream()
                .map(BoundExtractedResult::getReferent)
                .toList();

		// Sort results by similarities but don't take length into account
        final Function<BoundExtractedResult<Command.Choice>, Command.Choice> mapping = switch (type) {
            case STRING -> STRING_MAPPING;
            case INTEGER -> INTEGER_MAPPING;
            case NUMBER -> NUMBER_MAPPING;
            default -> throw new IllegalArgumentException("Invalid autocompletion option type: " + type);
        };
		return FuzzySearch.extractTop(input, bigLengthDiffResults, toStringFunction, FuzzySearch::ratio, OptionData.MAX_CHOICES).stream()
                .map(mapping)
                .toList();
    }

    private LazyUtilities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
