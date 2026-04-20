package xyz.srnyx.lazylibrary.emoji;

import io.github.freya022.botcommands.api.components.utils.ButtonContent;

import net.dv8tion.jda.api.components.buttons.ButtonStyle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;


/**
 * A list of <a href="https://venox.network">Venox Network</a> {@link ApplicationEmojiWrapper emojis}
 */
public class LazyEmoji {
    /**
     * {@code ✅} Checkmark
     */
    @NotNull public static final ApplicationEmojiWrapper YES = new ApplicationEmojiWrapper(EmojiLoader.YES);

    /**
     * {@code ✅} Checkmark without background
     */
    @NotNull public static final ApplicationEmojiWrapper YES_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.YES_CLEAR);

    /**
     * {@code ❌} X
     */
    @NotNull public static final ApplicationEmojiWrapper NO = new ApplicationEmojiWrapper(EmojiLoader.NO);

    /**
     * {@code ❌} X without background
     */
    @NotNull public static final ApplicationEmojiWrapper NO_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.NO_CLEAR);

    /**
     * {@code ❌} Dark X without background
     */
    @NotNull public static final ApplicationEmojiWrapper NO_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.NO_CLEAR_DARK);

    /**
     * {@code /} Slash
     */
    @NotNull public static final ApplicationEmojiWrapper MAYBE = new ApplicationEmojiWrapper(EmojiLoader.MAYBE);

    /**
     * {@code /} Slash without background
     */
    @NotNull public static final ApplicationEmojiWrapper MAYBE_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.MAYBE_CLEAR);

    /**
     * {@code ⬅️} Long left arrow
     */
    @NotNull public static final ApplicationEmojiWrapper LEFT = new ApplicationEmojiWrapper(EmojiLoader.LEFT);

    /**
     * {@code ⬅️} Long left arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper LEFT_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.LEFT_CLEAR);

    /**
     * {@code ⬅️} Dark long left arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper LEFT_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.LEFT_CLEAR_DARK);

    /**
     * {@code ➡️} Long right arrow
     */
    @NotNull public static final ApplicationEmojiWrapper RIGHT = new ApplicationEmojiWrapper(EmojiLoader.RIGHT);

    /**
     * {@code ➡️} Long right arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper RIGHT_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.RIGHT_CLEAR);

    /**
     * {@code ➡️} Dark long right arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper RIGHT_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.RIGHT_CLEAR_DARK);

    /**
     * {@code ⬆️} Long up arrow
     */
    @NotNull public static final ApplicationEmojiWrapper UP = new ApplicationEmojiWrapper(EmojiLoader.UP);

    /**
     * {@code ⬆️} Long up arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper UP_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.UP_CLEAR);

    /**
     * {@code ⬆️} Dark long up arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper UP_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.UP_CLEAR_DARK);

    /**
     * {@code ⬇️} Long down arrow
     */
    @NotNull public static final ApplicationEmojiWrapper DOWN = new ApplicationEmojiWrapper(EmojiLoader.DOWN);

    /**
     * {@code ⬇️} Long down arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper DOWN_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.DOWN_CLEAR);

    /**
     * {@code ⬇️} Dark long down arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper DOWN_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.DOWN_CLEAR_DARK);

    /**
     * {@code ⏪} Double left arrow
     */
    @NotNull public static final ApplicationEmojiWrapper BACK = new ApplicationEmojiWrapper(EmojiLoader.BACKWARD);

    /**
     * {@code ⏪} Double left arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper BACK_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.BACKWARD_CLEAR);

    /**
     * {@code ⏪} Dark double left arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper BACK_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.BACKWARD_CLEAR_DARK);

    /**
     * {@code ◀️} Short left arrow
     */
    @NotNull public static final ApplicationEmojiWrapper LEFT2 = new ApplicationEmojiWrapper(EmojiLoader.LEFT2);

    /**
     * {@code ◀️} Short left arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper LEFT2_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.LEFT2_CLEAR);

    /**
     * {@code ◀️} Dark short left arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper LEFT2_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.LEFT2_CLEAR_DARK);

    /**
     * {@code ▶️} Short right arrow
     */
    @NotNull public static final ApplicationEmojiWrapper RIGHT2 = new ApplicationEmojiWrapper(EmojiLoader.RIGHT2);

    /**
     * {@code ▶️} Short right arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper RIGHT2_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.RIGHT2_CLEAR);

    /**
     * {@code ▶️} Dark short right arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper RIGHT2_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.RIGHT2_CLEAR_DARK);

    /**
     * {@code 🔼} Short up arrow
     */
    @NotNull public static final ApplicationEmojiWrapper UP2 = new ApplicationEmojiWrapper(EmojiLoader.UP2);

    /**
     * {@code 🔼} Short up arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper UP2_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.UP2_CLEAR);

    /**
     * {@code 🔼} Dark short up arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper UP2_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.UP2_CLEAR_DARK);

    /**
     * {@code 🔽} Short down arrow
     */
    @NotNull public static final ApplicationEmojiWrapper DOWN2 = new ApplicationEmojiWrapper(EmojiLoader.DOWN2);

    /**
     * {@code 🔽} Short down arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper DOWN2_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.DOWN2_CLEAR);

    /**
     * {@code 🔽} Dark short down arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper DOWN2_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.DOWN2_CLEAR_DARK);

    /**
     * {@code ⏩} Double right arrow
     */
    @NotNull public static final ApplicationEmojiWrapper FORWARD = new ApplicationEmojiWrapper(EmojiLoader.FORWARD);

    /**
     * {@code ⏩} Double right arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper FORWARD_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.FORWARD_CLEAR);

    /**
     * {@code ⏩} Dark double right arrow without background
     */
    @NotNull public static final ApplicationEmojiWrapper FORWARD_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.FORWARD_CLEAR_DARK);

    /**
     * {@code ⚠️} Exclamation mark warning symbol
     */
    @NotNull public static final ApplicationEmojiWrapper WARNING = new ApplicationEmojiWrapper(EmojiLoader.WARNING);

    /**
     * {@code ⚠️} Exclamation mark warning symbol without background
     */
    @NotNull public static final ApplicationEmojiWrapper WARNING_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.WARNING_CLEAR);

    /**
     * {@code 🗑️} Trash can
     */
    @NotNull public static final ApplicationEmojiWrapper TRASH = new ApplicationEmojiWrapper(EmojiLoader.TRASH);

    /**
     * {@code 🗑️} Trash can without background
     */
    @NotNull public static final ApplicationEmojiWrapper TRASH_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.TRASH_CLEAR);

    /**
     * {@code 🗑️} Dark trash can without background
     */
    @NotNull public static final ApplicationEmojiWrapper TRASH_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.TRASH_CLEAR_DARK);

    /**
     * {@code 💬} Chat bubble
     */
    @NotNull public static final ApplicationEmojiWrapper CHAT = new ApplicationEmojiWrapper(EmojiLoader.CHAT);

    /**
     * {@code 💬} Chat bubble without background
     */
    @NotNull public static final ApplicationEmojiWrapper CHAT_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.CHAT_CLEAR);

    /**
     * {@code 🎫} Ticket
     */
    @NotNull public static final ApplicationEmojiWrapper TICKET = new ApplicationEmojiWrapper(EmojiLoader.TICKET);

    /**
     * {@code 🎫} Ticket without background
     */
    @NotNull public static final ApplicationEmojiWrapper TICKET_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.TICKET_CLEAR);

    /**
     * {@code 🎫} Dark ticket without background
     */
    @NotNull public static final ApplicationEmojiWrapper TICKET_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.TICKET_CLEAR_DARK);

    /**
     * {@code 🔒} Padlock
     */
    @NotNull public static final ApplicationEmojiWrapper LOCK = new ApplicationEmojiWrapper(EmojiLoader.LOCK);

    /**
     * {@code 🔒} Padlock without background
     */
    @NotNull public static final ApplicationEmojiWrapper LOCK_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.LOCK_CLEAR);

    /**
     * {@code 🔒} Dark padlock without background
     */
    @NotNull public static final ApplicationEmojiWrapper LOCK_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.LOCK_CLEAR_DARK);

    /**
     * {@code 🔓} Unlocked padlock
     */
    @NotNull public static final ApplicationEmojiWrapper UNLOCK = new ApplicationEmojiWrapper(EmojiLoader.UNLOCK);

    /**
     * {@code 🔓} Unlocked padlock without background
     */
    @NotNull public static final ApplicationEmojiWrapper UNLOCK_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.UNLOCK_CLEAR);

    /**
     * {@code 🔓} Dark unlocked padlock without background
     */
    @NotNull public static final ApplicationEmojiWrapper UNLOCK_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.UNLOCK_CLEAR_DARK);

    /**
     * {@code 🕒} Clock
     */
    @NotNull public static final ApplicationEmojiWrapper CLOCK = new ApplicationEmojiWrapper(EmojiLoader.CLOCK);

    /**
     * {@code 🕒} Clock without background
     */
    @NotNull public static final ApplicationEmojiWrapper CLOCK_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.CLOCK_CLEAR);

    /**
     * {@code 🕒} Dark clock without background
     */
    @NotNull public static final ApplicationEmojiWrapper CLOCK_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.CLOCK_CLEAR_DARK);

    /**
     * {@code ℹ️} Information {@code i} symbol
     */
    @NotNull public static final ApplicationEmojiWrapper INFO = new ApplicationEmojiWrapper(EmojiLoader.INFO);

    /**
     * {@code ℹ️} Information {@code i} symbol without background
     */
    @NotNull public static final ApplicationEmojiWrapper INFO_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.INFO_CLEAR);

    /**
     * {@code ℹ️} Dark information {@code i} symbol without background
     */
    @NotNull public static final ApplicationEmojiWrapper INFO_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.INFO_CLEAR_DARK);

    /**
     * {@code 👋} Wave
     */
    @NotNull public static final ApplicationEmojiWrapper WAVE = new ApplicationEmojiWrapper(EmojiLoader.WAVE);

    /**
     * {@code 👋} Wave without background
     */
    @NotNull public static final ApplicationEmojiWrapper WAVE_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.WAVE_CLEAR);

    /**
     * {@code 👋} Dark wave without background
     */
    @NotNull public static final ApplicationEmojiWrapper WAVE_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.WAVE_CLEAR_DARK);

    /**
     * {@code ...} Three loading dots
     */
    @NotNull public static final ApplicationEmojiWrapper LOAD = new ApplicationEmojiWrapper(EmojiLoader.LOAD);

    /**
     * {@code ...} Three loading dots without background
     */
    @NotNull public static final ApplicationEmojiWrapper LOAD_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.LOAD_CLEAR);

    /**
     * {@code ...} Three dark loading dots without background
     */
    @NotNull public static final ApplicationEmojiWrapper LOAD_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.LOAD_CLEAR_DARK);

    /**
     * {@code ?} Question mark
     */
    @NotNull public static final ApplicationEmojiWrapper QUESTION = new ApplicationEmojiWrapper(EmojiLoader.QUESTION);

    /**
     * {@code ?} Question mark without background
     */
    @NotNull public static final ApplicationEmojiWrapper QUESTION_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.QUESTION_CLEAR);

    /**
     * {@code ?} Dark question mark without background
     */
    @NotNull public static final ApplicationEmojiWrapper QUESTION_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.QUESTION_CLEAR_DARK);

    /**
     * {@code @} At symbol
     */
    @NotNull public static final ApplicationEmojiWrapper AT = new ApplicationEmojiWrapper(EmojiLoader.AT);

    /**
     * {@code @} At symbol without background
     */
    @NotNull public static final ApplicationEmojiWrapper AT_CLEAR = new ApplicationEmojiWrapper(EmojiLoader.AT_CLEAR);

    /**
     * {@code @} Dark at symbol without background
     */
    @NotNull public static final ApplicationEmojiWrapper AT_CLEAR_DARK = new ApplicationEmojiWrapper(EmojiLoader.AT_CLEAR_DARK);

    /**
     * A set of all emoji IDs in this class for easy access
     */
    @NotNull @Unmodifiable public static final Set<Long> IDS;
    static {
        final Set<Long> ids = new HashSet<>();
        for (final Field field : LazyEmoji.class.getDeclaredFields()) {
            if (ApplicationEmojiWrapper.class.isAssignableFrom(field.getType())) try {
                ids.add(((ApplicationEmojiWrapper) field.get(null)).getIdLong());
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        IDS = Set.copyOf(ids);
    }

    /**
     * Returns a {@link ButtonContent} for the provided {@link ApplicationEmojiWrapper} with the specified {@link ButtonStyle} and optional text.
     * If text is null the content will only include the emoji.
     *
     * @param   emoji   the {@link ApplicationEmojiWrapper} to create the {@link ButtonContent} for
     * @param   style   the {@link ButtonStyle} of the button
     * @param   text    the optional text to include in the {@link ButtonContent}, if null the content will only include the emoji
     *
     * @return          a {@link ButtonContent} for the provided {@link ApplicationEmojiWrapper} with the specified {@link ButtonStyle} and optional text
     */
    @NotNull
    public static ButtonContent getButtonContent(@NotNull ApplicationEmojiWrapper emoji, @NotNull ButtonStyle style, @Nullable String text) {
        return text == null ? ButtonContent.fromEmoji(style, emoji) : ButtonContent.fromEmoji(style, text, emoji);
    }

    /**
     * Returns a {@link ButtonContent} for the provided {@link ApplicationEmojiWrapper} with the specified {@link ButtonStyle} and no text
     *
     * @param   emoji   the {@link ApplicationEmojiWrapper} to create the {@link ButtonContent} for
     * @param   style   the {@link ButtonStyle} of the button
     *
     * @return          a {@link ButtonContent} for the provided {@link ApplicationEmojiWrapper} with the specified {@link ButtonStyle} and no text
     */
    @NotNull
    public static ButtonContent getButtonContent(@NotNull ApplicationEmojiWrapper emoji, @NotNull ButtonStyle style) {
        return getButtonContent(emoji, style, null);
    }

    /**
     * Cannot be instantiated
     */
    private LazyEmoji() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
