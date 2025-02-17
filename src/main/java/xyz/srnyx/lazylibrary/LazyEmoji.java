package xyz.srnyx.lazylibrary;

import com.freya02.botcommands.api.utils.ButtonContent;

import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A list of <a href="https://venox.network">Venox Network</a> {@link Emoji emojis}
 */
public enum LazyEmoji {
    /**
     * Checkmark
     */
    YES(1095912430443450469L),
    /**
     * Checkmark without background
     */
    YES_CLEAR(1095912345978552340L),
    /**
     * X
     */
    NO(1095912334792331326L),
    /**
     * X without background
     */
    NO_CLEAR(1095912336163864576L),
    /**
     * Dark X without background
     */
    NO_CLEAR_DARK(1095912337162129418L),
    /**
     * Slash
     */
    MAYBE(1095912332351242312L),
    /**
     * Slash without background
     */
    MAYBE_CLEAR(1095912333768933476L),
    /**
     * Long left arrow
     */
    LEFT(1095912329767571506L),
    /**
     * Long left arrow without background
     */
    LEFT_CLEAR(1095912331210399834L),
    /**
     * Dark long left arrow without background
     */
    LEFT_CLEAR_DARK(1097032738839732364L),
    /**
     * Long right arrow
     */
    RIGHT(1095912338248454196L),
    /**
     * Long right arrow without background
     */
    RIGHT_CLEAR(1095912340416893039L),
    /**
     * Dark long right arrow without background
     */
    RIGHT_CLEAR_DARK(1097032739447918644L),
    /**
     * Long up arrow
     */
    UP(1096932211938304160L),
    /**
     * Long up arrow without background
     */
    UP_CLEAR(1096932212814917772L),
    /**
     * Dark long up arrow without background
     */
    UP_CLEAR_DARK(1097032042585280614L),
    /**
     * Long down arrow
     */
    DOWN(1096932209237164164L),
    /**
     * Long down arrow without background
     */
    DOWN_CLEAR(1096932210650656878L),
    /**
     * Dark long down arrow without background
     */
    DOWN_CLEAR_DARK(1097032737187184751L),
    /**
     * Double left arrow
     */
    BACK(1298360906857578557L),
    /**
     * Double left arrow without background
     */
    BACK_CLEAR(1298360908145496144L),
    /**
     * Dark double left arrow without background
     */
    BACK_CLEAR_DARK(1298360909277954108L),
    /**
     * Short left arrow
     */
    LEFT2(1298411480168923146L),
    /**
     * Short left arrow without background
     */
    LEFT2_CLEAR(1298411480940806165L),
    /**
     * Dark short left arrow without background
     */
    LEFT2_CLEAR_DARK(1298411482257559585L),
    /**
     * Short right arrow
     */
    RIGHT2(1298411491099283516L),
    /**
     * Short right arrow without background
     */
    RIGHT2_CLEAR(1298411599018590249L),
    /**
     * Dark short right arrow without background
     */
    RIGHT2_CLEAR_DARK(1298411494790402058L),
    /**
     * Short up arrow
     */
    UP2(1315113070892421230L),
    /**
     * Short up arrow without background
     */
    UP2_CLEAR(1315113072737779742L),
    /**
     * Dark short up arrow without background
     */
    UP2_CLEAR_DARK(1315113073840750654L),
    /**
     * Short down arrow
     */
    DOWN2(1315113084024786974L),
    /**
     * Short down arrow without background
     */
    DOWN2_CLEAR(1315113084850802698L),
    /**
     * Dark short down arrow without background
     */
    DOWN2_CLEAR_DARK(1315113085723480074L),
    /**
     * Double right arrow
     */
    FORWARD(1298360910410158121L),
    /**
     * Double right arrow without background
     */
    FORWARD_CLEAR(1298360911286894632L),
    /**
     * Dark double right arrow without background
     */
    FORWARD_CLEAR_DARK(1298360912477945938L),
    /**
     * Exclamation mark warning symbol
     */
    WARNING(1096159841120165908L),
    /**
     * Exclamation mark warning symbol without background
     */
    WARNING_CLEAR(1096159842315534409L),
    /**
     * Trash can
     */
    TRASH(1095912427125747883L),
    /**
     * Trash can without background
     */
    TRASH_CLEAR(1095912428597948507L),
    /**
     * Dark trash can without background
     */
    TRASH_CLEAR_DARK(1095912429587800135L),
    /**
     * Chat bubble
     */
    CHAT(1096272195841441822L),
    /**
     * Chat bubble without background
     */
    CHAT_CLEAR(1096272196864835584L),
    /**
     * Ticket
     */
    TICKET(1298361698050375690L),
    /**
     * Ticket without background
     */
    TICKET_CLEAR(1298361698981253140L),
    /**
     * Dark ticket without background
     */
    TICKET_CLEAR_DARK(1298361700583735472L),
    /**
     * Padlock
     */
    LOCK(1298362723700314212L),
    /**
     * Padlock without background
     */
    LOCK_CLEAR(1298362724987830412L),
    /**
     * Dark padlock without background
     */
    LOCK_CLEAR_DARK(1298362731573022782L),
    /**
     * Unlocked padlock
     */
    UNLOCK(1298400380585119764L),
    /**
     * Unlocked padlock without background
     */
    UNLOCK_CLEAR(1298400381793206302L),
    /**
     * Dark unlocked padlock without background
     */
    UNLOCK_CLEAR_DARK(1298400382648844420L),
    /**
     * Clock
     */
    CLOCK(1298363518118006845L),
    /**
     * Clock without background
     */
    CLOCK_CLEAR(1298363519548391454L),
    /**
     * Dark clock without background
     */
    CLOCK_CLEAR_DARK(1298363520907219075L),
    /**
     * Information {@code i} symbol
     */
    INFO(1298365351980961852L),
    /**
     * Information {@code i} symbol without background
     */
    INFO_CLEAR(1298365353717530744L),
    /**
     * Dark information {@code i} symbol without background
     */
    INFO_CLEAR_DARK(1298365354812112917L),
    /**
     * Wave
     */
    WAVE(1298366552994873476L),
    /**
     * Wave without background
     */
    WAVE_CLEAR(1298366555025047723L),
    /**
     * Dark wave without background
     */
    WAVE_CLEAR_DARK(1298366556140601345L),
    /**
     * Three loading dots
     */
    LOAD(1298411483595538483L),
    /**
     * Three loading dots without background
     */
    LOAD_CLEAR(1298411485395157032L),
    /**
     * Three dark loading dots without background
     */
    LOAD_CLEAR_DARK(1298411486112252088L),
    /**
     * Question mark
     */
    QUESTION(1298411486825152603L),
    /**
     * Question mark without background
     */
    QUESTION_CLEAR(1298411488158945311L),
    /**
     * Dark question mark without background
     */
    QUESTION_CLEAR_DARK(1298411642358337578L);

    @NotNull public final CustomEmoji emoji;

    LazyEmoji(long id, boolean animated) {
        this.emoji = Emoji.fromCustom("vn" + name().toLowerCase().replace("_", ""), id, animated);
    }

    LazyEmoji(long id) {
        this(id, false);
    }

    /**
     * Construct a {@link ButtonContent} from this {@link LazyEmoji}
     *
     * @param   text    the text to display on the button
     *
     * @return          the constructed {@link ButtonContent}
     */
    @NotNull
    public ButtonContent getButtonContent(@Nullable String text) {
        return new ButtonContent(text, emoji);
    }

    /**
     * Construct a {@link ButtonContent} from this {@link LazyEmoji} with no text
     *
     * @return  the constructed {@link ButtonContent}
     */
    @NotNull
    public ButtonContent getButtonContent() {
        return getButtonContent(null);
    }

    @Override @NotNull
    public String toString() {
        return emoji.getFormatted();
    }
}
