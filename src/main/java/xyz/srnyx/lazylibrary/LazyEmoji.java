package xyz.srnyx.lazylibrary;

import io.github.freya022.botcommands.api.components.utils.ButtonContent;

import net.dv8tion.jda.api.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A list of <a href="https://venox.network">Venox Network</a> {@link Emoji emojis}
 */
public enum LazyEmoji {
    /**
     * {@code ✅} Checkmark
     */
    YES(1095912430443450469L),
    /**
     * {@code ✅} Checkmark without background
     */
    YES_CLEAR(1095912345978552340L),
    /**
     * {@code ❌} X
     */
    NO(1095912334792331326L),
    /**
     * {@code ❌} X without background
     */
    NO_CLEAR(1095912336163864576L),
    /**
     * {@code ❌} Dark X without background
     */
    NO_CLEAR_DARK(1095912337162129418L),
    /**
     * {@code /} Slash
     */
    MAYBE(1095912332351242312L),
    /**
     * {@code /} Slash without background
     */
    MAYBE_CLEAR(1095912333768933476L),
    /**
     * {@code ⬅️} Long left arrow
     */
    LEFT(1095912329767571506L),
    /**
     * {@code ⬅️} Long left arrow without background
     */
    LEFT_CLEAR(1095912331210399834L),
    /**
     * {@code ⬅️} Dark long left arrow without background
     */
    LEFT_CLEAR_DARK(1097032738839732364L),
    /**
     * {@code ➡️} Long right arrow
     */
    RIGHT(1095912338248454196L),
    /**
     * {@code ➡️} Long right arrow without background
     */
    RIGHT_CLEAR(1095912340416893039L),
    /**
     * {@code ➡️} Dark long right arrow without background
     */
    RIGHT_CLEAR_DARK(1097032739447918644L),
    /**
     * {@code ⬆️} Long up arrow
     */
    UP(1096932211938304160L),
    /**
     * {@code ⬆️} Long up arrow without background
     */
    UP_CLEAR(1096932212814917772L),
    /**
     * {@code ⬆️} Dark long up arrow without background
     */
    UP_CLEAR_DARK(1097032042585280614L),
    /**
     * {@code ⬇️} Long down arrow
     */
    DOWN(1096932209237164164L),
    /**
     * {@code ⬇️} Long down arrow without background
     */
    DOWN_CLEAR(1096932210650656878L),
    /**
     * {@code ⬇️} Dark long down arrow without background
     */
    DOWN_CLEAR_DARK(1097032737187184751L),
    /**
     * {@code ⏪} Double left arrow
     */
    BACK(1298360906857578557L),
    /**
     * {@code ⏪} Double left arrow without background
     */
    BACK_CLEAR(1298360908145496144L),
    /**
     * {@code ⏪} Dark double left arrow without background
     */
    BACK_CLEAR_DARK(1298360909277954108L),
    /**
     * {@code ◀️} Short left arrow
     */
    LEFT2(1298411480168923146L),
    /**
     * {@code ◀️} Short left arrow without background
     */
    LEFT2_CLEAR(1298411480940806165L),
    /**
     * {@code ◀️} Dark short left arrow without background
     */
    LEFT2_CLEAR_DARK(1298411482257559585L),
    /**
     * {@code ▶️} Short right arrow
     */
    RIGHT2(1298411491099283516L),
    /**
     * {@code ▶️} Short right arrow without background
     */
    RIGHT2_CLEAR(1298411599018590249L),
    /**
     * {@code ▶️} Dark short right arrow without background
     */
    RIGHT2_CLEAR_DARK(1298411494790402058L),
    /**
     * {@code 🔼} Short up arrow
     */
    UP2(1315113070892421230L),
    /**
     * {@code 🔼} Short up arrow without background
     */
    UP2_CLEAR(1315113072737779742L),
    /**
     * {@code 🔼} Dark short up arrow without background
     */
    UP2_CLEAR_DARK(1315113073840750654L),
    /**
     * {@code 🔽} Short down arrow
     */
    DOWN2(1315113084024786974L),
    /**
     * {@code 🔽} Short down arrow without background
     */
    DOWN2_CLEAR(1315113084850802698L),
    /**
     * {@code 🔽} Dark short down arrow without background
     */
    DOWN2_CLEAR_DARK(1315113085723480074L),
    /**
     * {@code ⏩} Double right arrow
     */
    FORWARD(1298360910410158121L),
    /**
     * {@code ⏩} Double right arrow without background
     */
    FORWARD_CLEAR(1298360911286894632L),
    /**
     * {@code ⏩} Dark double right arrow without background
     */
    FORWARD_CLEAR_DARK(1298360912477945938L),
    /**
     * {@code ⚠️} Exclamation mark warning symbol
     */
    WARNING(1096159841120165908L),
    /**
     * {@code ⚠️} Exclamation mark warning symbol without background
     */
    WARNING_CLEAR(1096159842315534409L),
    /**
     * {@code 🗑️} Trash can
     */
    TRASH(1095912427125747883L),
    /**
     * {@code 🗑️} Trash can without background
     */
    TRASH_CLEAR(1095912428597948507L),
    /**
     * {@code 🗑️} Dark trash can without background
     */
    TRASH_CLEAR_DARK(1095912429587800135L),
    /**
     * {@code 💬} Chat bubble
     */
    CHAT(1096272195841441822L),
    /**
     * {@code 💬} Chat bubble without background
     */
    CHAT_CLEAR(1096272196864835584L),
    /**
     * {@code 🎫} Ticket
     */
    TICKET(1298361698050375690L),
    /**
     * {@code 🎫} Ticket without background
     */
    TICKET_CLEAR(1298361698981253140L),
    /**
     * {@code 🎫} Dark ticket without background
     */
    TICKET_CLEAR_DARK(1298361700583735472L),
    /**
     * {@code 🔒} Padlock
     */
    LOCK(1298362723700314212L),
    /**
     * {@code 🔒} Padlock without background
     */
    LOCK_CLEAR(1298362724987830412L),
    /**
     * {@code 🔒} Dark padlock without background
     */
    LOCK_CLEAR_DARK(1298362731573022782L),
    /**
     * {@code 🔓} Unlocked padlock
     */
    UNLOCK(1298400380585119764L),
    /**
     * {@code 🔓} Unlocked padlock without background
     */
    UNLOCK_CLEAR(1298400381793206302L),
    /**
     * {@code 🔓} Dark unlocked padlock without background
     */
    UNLOCK_CLEAR_DARK(1298400382648844420L),
    /**
     * {@code 🕒} Clock
     */
    CLOCK(1298363518118006845L),
    /**
     * {@code 🕒} Clock without background
     */
    CLOCK_CLEAR(1298363519548391454L),
    /**
     * {@code 🕒} Dark clock without background
     */
    CLOCK_CLEAR_DARK(1298363520907219075L),
    /**
     * {@code ℹ️} Information {@code i} symbol
     */
    INFO(1298365351980961852L),
    /**
     * {@code ℹ️} Information {@code i} symbol without background
     */
    INFO_CLEAR(1298365353717530744L),
    /**
     * {@code ℹ️} Dark information {@code i} symbol without background
     */
    INFO_CLEAR_DARK(1298365354812112917L),
    /**
     * {@code 👋} Wave
     */
    WAVE(1298366552994873476L),
    /**
     * {@code 👋} Wave without background
     */
    WAVE_CLEAR(1298366555025047723L),
    /**
     * {@code 👋} Dark wave without background
     */
    WAVE_CLEAR_DARK(1298366556140601345L),
    /**
     * {@code ...} Three loading dots
     */
    LOAD(1298411483595538483L),
    /**
     * {@code ...} Three loading dots without background
     */
    LOAD_CLEAR(1298411485395157032L),
    /**
     * {@code ...} Three dark loading dots without background
     */
    LOAD_CLEAR_DARK(1298411486112252088L),
    /**
     * {@code ?} Question mark
     */
    QUESTION(1298411486825152603L),
    /**
     * {@code ?} Question mark without background
     */
    QUESTION_CLEAR(1298411488158945311L),
    /**
     * {@code ?} Dark question mark without background
     */
    QUESTION_CLEAR_DARK(1298411642358337578L),
    /**
     * {@code @} At symbol
     */
    AT(1476730763905859745L),
    /**
     * {@code @} At symbol without background
     */
    AT_CLEAR(1476730811985432716L),
    /**
     * {@code @} Dark at symbol without background
     */
    AT_CLEAR_DARK(1476730836752797796L);

    /**
     * The {@link CustomEmoji} for this {@link LazyEmoji}
     */
    @NotNull public final CustomEmoji emoji;

    LazyEmoji(long id, boolean animated) {
        this.emoji = Emoji.fromCustom("vn" + name().toLowerCase().replace("_", ""), id, animated);
    }

    LazyEmoji(long id) {
        this(id, false);
    }

    @NotNull
    public ButtonContent getButtonContent(@NotNull ButtonStyle style, @Nullable String text) {
        return text == null ? ButtonContent.fromEmoji(style, emoji) : ButtonContent.fromEmoji(style, text, emoji);
    }

    @NotNull
    public ButtonContent getButtonContent(@NotNull ButtonStyle style) {
        return getButtonContent(style, null);
    }

    @Override @NotNull
    public String toString() {
        return emoji.getFormatted();
    }
}
