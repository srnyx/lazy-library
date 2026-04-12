package xyz.srnyx.lazylibrary.emoji;

import io.github.freya022.botcommands.api.emojis.AppEmojisRegistry;
import io.github.freya022.botcommands.api.emojis.annotations.AppEmoji;
import io.github.freya022.botcommands.api.emojis.annotations.AppEmojiContainer;

import net.dv8tion.jda.api.entities.emoji.ApplicationEmoji;


/**
 * A class that loads all emojis from the application and stores them as static final variables for easy access
 *
 * @see LazyEmoji
 */
@AppEmojiContainer
public class EmojiLoader {
    /**
     * {@code ✅} Checkmark
     */
    @AppEmoji
    public static final ApplicationEmoji YES = AppEmojisRegistry.get("YES");

    /**
     * {@code ✅} Checkmark without background
     */
    @AppEmoji
    public static final ApplicationEmoji YES_CLEAR = AppEmojisRegistry.get("YES_CLEAR");

    /**
     * {@code ❌} X
     */
    @AppEmoji
    public static final ApplicationEmoji NO = AppEmojisRegistry.get("NO");

    /**
     * {@code ❌} X without background
     */
    @AppEmoji
    public static final ApplicationEmoji NO_CLEAR = AppEmojisRegistry.get("NO_CLEAR");

    /**
     * {@code ❌} Dark X without background
     */
    @AppEmoji
    public static final ApplicationEmoji NO_CLEAR_DARK = AppEmojisRegistry.get("NO_CLEAR_DARK");

    /**
     * {@code /} Slash
     */
    @AppEmoji
    public static final ApplicationEmoji MAYBE = AppEmojisRegistry.get("MAYBE");

    /**
     * {@code /} Slash without background
     */
    @AppEmoji
    public static final ApplicationEmoji MAYBE_CLEAR = AppEmojisRegistry.get("MAYBE_CLEAR");

    /**
     * {@code ⬅️} Long left arrow
     */
    @AppEmoji
    public static final ApplicationEmoji LEFT = AppEmojisRegistry.get("LEFT");

    /**
     * {@code ⬅️} Long left arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji LEFT_CLEAR = AppEmojisRegistry.get("LEFT_CLEAR");

    /**
     * {@code ⬅️} Dark long left arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji LEFT_CLEAR_DARK = AppEmojisRegistry.get("LEFT_CLEAR_DARK");

    /**
     * {@code ➡️} Long right arrow
     */
    @AppEmoji
    public static final ApplicationEmoji RIGHT = AppEmojisRegistry.get("RIGHT");

    /**
     * {@code ➡️} Long right arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji RIGHT_CLEAR = AppEmojisRegistry.get("RIGHT_CLEAR");

    /**
     * {@code ➡️} Dark long right arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji RIGHT_CLEAR_DARK = AppEmojisRegistry.get("RIGHT_CLEAR_DARK");

    /**
     * {@code ⬆️} Long up arrow
     */
    @AppEmoji
    public static final ApplicationEmoji UP = AppEmojisRegistry.get("UP");

    /**
     * {@code ⬆️} Long up arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji UP_CLEAR = AppEmojisRegistry.get("UP_CLEAR");

    /**
     * {@code ⬆️} Dark long up arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji UP_CLEAR_DARK = AppEmojisRegistry.get("UP_CLEAR_DARK");

    /**
     * {@code ⬇️} Long down arrow
     */
    @AppEmoji
    public static final ApplicationEmoji DOWN = AppEmojisRegistry.get("DOWN");

    /**
     * {@code ⬇️} Long down arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji DOWN_CLEAR = AppEmojisRegistry.get("DOWN_CLEAR");

    /**
     * {@code ⬇️} Dark long down arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji DOWN_CLEAR_DARK = AppEmojisRegistry.get("DOWN_CLEAR_DARK");

    /**
     * {@code ⏪} Double left arrow
     */
    @AppEmoji
    public static final ApplicationEmoji BACKWARD = AppEmojisRegistry.get("BACKWARD");

    /**
     * {@code ⏪} Double left arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji BACKWARD_CLEAR = AppEmojisRegistry.get("BACKWARD_CLEAR");

    /**
     * {@code ⏪} Dark double left arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji BACKWARD_CLEAR_DARK = AppEmojisRegistry.get("BACKWARD_CLEAR_DARK");

    /**
     * {@code ◀️} Short left arrow
     */
    @AppEmoji
    public static final ApplicationEmoji LEFT2 = AppEmojisRegistry.get("LEFT2");

    /**
     * {@code ◀️} Short left arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji LEFT2_CLEAR = AppEmojisRegistry.get("LEFT2_CLEAR");

    /**
     * {@code ◀️} Dark short left arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji LEFT2_CLEAR_DARK = AppEmojisRegistry.get("LEFT2_CLEAR_DARK");

    /**
     * {@code ▶️} Short right arrow
     */
    @AppEmoji
    public static final ApplicationEmoji RIGHT2 = AppEmojisRegistry.get("RIGHT2");

    /**
     * {@code ▶️} Short right arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji RIGHT2_CLEAR = AppEmojisRegistry.get("RIGHT2_CLEAR");

    /**
     * {@code ▶️} Dark short right arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji RIGHT2_CLEAR_DARK = AppEmojisRegistry.get("RIGHT2_CLEAR_DARK");

    /**
     * {@code 🔼} Short up arrow
     */
    @AppEmoji
    public static final ApplicationEmoji UP2 = AppEmojisRegistry.get("UP2");

    /**
     * {@code 🔼} Short up arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji UP2_CLEAR = AppEmojisRegistry.get("UP2_CLEAR");

    /**
     * {@code 🔼} Dark short up arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji UP2_CLEAR_DARK = AppEmojisRegistry.get("UP2_CLEAR_DARK");

    /**
     * {@code 🔽} Short down arrow
     */
    @AppEmoji
    public static final ApplicationEmoji DOWN2 = AppEmojisRegistry.get("DOWN2");

    /**
     * {@code 🔽} Short down arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji DOWN2_CLEAR = AppEmojisRegistry.get("DOWN2_CLEAR");

    /**
     * {@code 🔽} Dark short down arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji DOWN2_CLEAR_DARK = AppEmojisRegistry.get("DOWN2_CLEAR_DARK");

    /**
     * {@code ⏩} Double right arrow
     */
    @AppEmoji
    public static final ApplicationEmoji FORWARD = AppEmojisRegistry.get("FORWARD");

    /**
     * {@code ⏩} Double right arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji FORWARD_CLEAR = AppEmojisRegistry.get("FORWARD_CLEAR");

    /**
     * {@code ⏩} Dark double right arrow without background
     */
    @AppEmoji
    public static final ApplicationEmoji FORWARD_CLEAR_DARK = AppEmojisRegistry.get("FORWARD_CLEAR_DARK");

    /**
     * {@code ⚠️} Exclamation mark warning symbol
     */
    @AppEmoji
    public static final ApplicationEmoji WARNING = AppEmojisRegistry.get("WARNING");

    /**
     * {@code ⚠️} Exclamation mark warning symbol without background
     */
    @AppEmoji
    public static final ApplicationEmoji WARNING_CLEAR = AppEmojisRegistry.get("WARNING_CLEAR");

    /**
     * {@code 🗑️} Trash can
     */
    @AppEmoji
    public static final ApplicationEmoji TRASH = AppEmojisRegistry.get("TRASH");

    /**
     * {@code 🗑️} Trash can without background
     */
    @AppEmoji
    public static final ApplicationEmoji TRASH_CLEAR = AppEmojisRegistry.get("TRASH_CLEAR");

    /**
     * {@code 🗑️} Dark trash can without background
     */
    @AppEmoji
    public static final ApplicationEmoji TRASH_CLEAR_DARK = AppEmojisRegistry.get("TRASH_CLEAR_DARK");

    /**
     * {@code 💬} Chat bubble
     */
    @AppEmoji
    public static final ApplicationEmoji CHAT = AppEmojisRegistry.get("CHAT");

    /**
     * {@code 💬} Chat bubble without background
     */
    @AppEmoji
    public static final ApplicationEmoji CHAT_CLEAR = AppEmojisRegistry.get("CHAT_CLEAR");

    /**
     * {@code 🎫} Ticket
     */
    @AppEmoji
    public static final ApplicationEmoji TICKET = AppEmojisRegistry.get("TICKET");

    /**
     * {@code 🎫} Ticket without background
     */
    @AppEmoji
    public static final ApplicationEmoji TICKET_CLEAR = AppEmojisRegistry.get("TICKET_CLEAR");

    /**
     * {@code 🎫} Dark ticket without background
     */
    @AppEmoji
    public static final ApplicationEmoji TICKET_CLEAR_DARK = AppEmojisRegistry.get("TICKET_CLEAR_DARK");

    /**
     * {@code 🔒} Padlock
     */
    @AppEmoji
    public static final ApplicationEmoji LOCK = AppEmojisRegistry.get("LOCK");

    /**
     * {@code 🔒} Padlock without background
     */
    @AppEmoji
    public static final ApplicationEmoji LOCK_CLEAR = AppEmojisRegistry.get("LOCK_CLEAR");

    /**
     * {@code 🔒} Dark padlock without background
     */
    @AppEmoji
    public static final ApplicationEmoji LOCK_CLEAR_DARK = AppEmojisRegistry.get("LOCK_CLEAR_DARK");

    /**
     * {@code 🔓} Unlocked padlock
     */
    @AppEmoji
    public static final ApplicationEmoji UNLOCK = AppEmojisRegistry.get("UNLOCK");

    /**
     * {@code 🔓} Unlocked padlock without background
     */
    @AppEmoji
    public static final ApplicationEmoji UNLOCK_CLEAR = AppEmojisRegistry.get("UNLOCK_CLEAR");

    /**
     * {@code 🔓} Dark unlocked padlock without background
     */
    @AppEmoji
    public static final ApplicationEmoji UNLOCK_CLEAR_DARK = AppEmojisRegistry.get("UNLOCK_CLEAR_DARK");

    /**
     * {@code 🕒} Clock
     */
    @AppEmoji
    public static final ApplicationEmoji CLOCK = AppEmojisRegistry.get("CLOCK");

    /**
     * {@code 🕒} Clock without background
     */
    @AppEmoji
    public static final ApplicationEmoji CLOCK_CLEAR = AppEmojisRegistry.get("CLOCK_CLEAR");

    /**
     * {@code 🕒} Dark clock without background
     */
    @AppEmoji
    public static final ApplicationEmoji CLOCK_CLEAR_DARK = AppEmojisRegistry.get("CLOCK_CLEAR_DARK");

    /**
     * {@code ℹ️} Information {@code i} symbol
     */
    @AppEmoji
    public static final ApplicationEmoji INFO = AppEmojisRegistry.get("INFO");

    /**
     * {@code ℹ️} Information {@code i} symbol without background
     */
    @AppEmoji
    public static final ApplicationEmoji INFO_CLEAR = AppEmojisRegistry.get("INFO_CLEAR");

    /**
     * {@code ℹ️} Dark information {@code i} symbol without background
     */
    @AppEmoji
    public static final ApplicationEmoji INFO_CLEAR_DARK = AppEmojisRegistry.get("INFO_CLEAR_DARK");

    /**
     * {@code 👋} Wave
     */
    @AppEmoji
    public static final ApplicationEmoji WAVE = AppEmojisRegistry.get("WAVE");

    /**
     * {@code 👋} Wave without background
     */
    @AppEmoji
    public static final ApplicationEmoji WAVE_CLEAR = AppEmojisRegistry.get("WAVE_CLEAR");

    /**
     * {@code 👋} Dark wave without background
     */
    @AppEmoji
    public static final ApplicationEmoji WAVE_CLEAR_DARK = AppEmojisRegistry.get("WAVE_CLEAR_DARK");

    /**
     * {@code ...} Three loading dots
     */
    @AppEmoji
    public static final ApplicationEmoji LOAD = AppEmojisRegistry.get("LOAD");

    /**
     * {@code ...} Three loading dots without background
     */
    @AppEmoji
    public static final ApplicationEmoji LOAD_CLEAR = AppEmojisRegistry.get("LOAD_CLEAR");

    /**
     * {@code ...} Three dark loading dots without background
     */
    @AppEmoji
    public static final ApplicationEmoji LOAD_CLEAR_DARK = AppEmojisRegistry.get("LOAD_CLEAR_DARK");

    /**
     * {@code ?} Question mark
     */
    @AppEmoji
    public static final ApplicationEmoji QUESTION = AppEmojisRegistry.get("QUESTION");

    /**
     * {@code ?} Question mark without background
     */
    @AppEmoji
    public static final ApplicationEmoji QUESTION_CLEAR = AppEmojisRegistry.get("QUESTION_CLEAR");

    /**
     * {@code ?} Dark question mark without background
     */
    @AppEmoji
    public static final ApplicationEmoji QUESTION_CLEAR_DARK = AppEmojisRegistry.get("QUESTION_CLEAR_DARK");

    /**
     * {@code @} At symbol
     */
    @AppEmoji
    public static final ApplicationEmoji AT = AppEmojisRegistry.get("AT");

    /**
     * {@code @} At symbol without background
     */
    @AppEmoji
    public static final ApplicationEmoji AT_CLEAR = AppEmojisRegistry.get("AT_CLEAR");

    /**
     * {@code @} Dark at symbol without background
     */
    @AppEmoji
    public static final ApplicationEmoji AT_CLEAR_DARK = AppEmojisRegistry.get("AT_CLEAR_DARK");

    /**
     * No services needed
     */
    public EmojiLoader() {}
}
