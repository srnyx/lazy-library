package xyz.srnyx.lazylibrary;

import com.freya02.botcommands.api.utils.ButtonContent;

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
    YES("vnyes", 1095912430443450469L),
    /**
     * Checkmark without background
     */
    YES_CLEAR("vnyesclear", 1095912345978552340L),
    /**
     * X
     */
    NO("vnno", 1095912334792331326L),
    /**
     * X without background
     */
    NO_CLEAR("vnnoclear", 1095912336163864576L),
    /**
     * Dark X without background
     */
    NO_CLEAR_DARK("vnnocleardark", 1095912337162129418L),
    /**
     * Slash
     */
    MAYBE("vnmaybe", 1095912332351242312L),
    /**
     * Slash without background
     */
    MAYBE_CLEAR("vnmaybeclear", 1095912333768933476L),
    /**
     * Left arrow
     */
    LEFT("vnleft", 1095912329767571506L),
    /**
     * Left arrow without background
     */
    LEFT_CLEAR("vnleftclear", 1095912331210399834L),
    /**
     * Dark left arrow without background
     */
    LEFT_CLEAR_DARK("vnleftcleardark", 1097032738839732364L),
    /**
     * Right arrow
     */
    RIGHT("vnright", 1095912338248454196L),
    /**
     * Right arrow without background
     */
    RIGHT_CLEAR("vnrightclear", 1095912340416893039L),
    /**
     * Dark right arrow without background
     */
    RIGHT_CLEAR_DARK("vnrightcleardark", 1097032739447918644L),
    /**
     * Up arrow
     */
    UP("vnup", 1096932211938304160L),
    /**
     * Up arrow without background
     */
    UP_CLEAR("vnupclear", 1096932212814917772L),
    /**
     * Dark up arrow without background
     */
    UP_CLEAR_DARK("vnupcleardark", 1097032042585280614L),
    /**
     * Down arrow
     */
    DOWN("vndown", 1096932209237164164L),
    /**
     * Down arrow without background
     */
    DOWN_CLEAR("vndownclear", 1096932210650656878L),
    /**
     * Dark down arrow without background
     */
    DOWN_CLEAR_DARK("vndowncleardark", 1097032737187184751L),
    /**
     * Exclamation mark warning symbol
     */
    WARNING("vnwarning", 1096159841120165908L),
    /**
     * Exclamation mark warning symbol without background
     */
    WARNING_CLEAR("vnwarningclear", 1096159842315534409L),
    /**
     * Trash can
     */
    TRASH("vntrash", 1095912427125747883L),
    /**
     * Trash can without background
     */
    TRASH_CLEAR("vntrashclear", 1095912428597948507L),
    /**
     * Dark trash can without background
     */
    TRASH_CLEAR_DARK("vntrashcleardark", 1095912429587800135L),
    /**
     * Chat bubble
     */
    CHAT("vnchat", 1096272195841441822L),
    /**
     * Chat bubble without background
     */
    CHAT_CLEAR("vnchatclear", 1096272196864835584L);

    @NotNull private final String name;
    private final long id;
    private final boolean animated;

    LazyEmoji(@NotNull String name, long id, boolean animated) {
        this.name = name;
        this.id = id;
        this.animated = animated;
    }

    LazyEmoji(@NotNull String name, long id) {
        this(name, id, false);
    }

    /**
     * Forms a {@link Emoji} from this {@link LazyEmoji}
     *
     * @return  the {@link Emoji} formed
     */
    @NotNull
    public Emoji getEmoji() {
        return Emoji.fromCustom(name, id, animated);
    }

    /**
     * Construct a {@link ButtonContent} from this {@link LazyEmoji}
     *
     * @param   text    the text to display on the button
     *
     * @return          the {@link ButtonContent} constructed
     */
    @NotNull
    public ButtonContent getButtonContent(@Nullable String text) {
        return new ButtonContent(text, getEmoji());
    }

    @Override @NotNull
    public String toString() {
        return getEmoji().getFormatted();
    }
}
