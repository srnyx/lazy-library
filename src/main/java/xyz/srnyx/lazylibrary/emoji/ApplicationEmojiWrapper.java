package xyz.srnyx.lazylibrary.emoji;

import net.dv8tion.jda.api.entities.emoji.*;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.emoji.ApplicationEmojiImpl;

import org.jetbrains.annotations.NotNull;


/**
 * A wrapper for {@link ApplicationEmojiImpl} that adds a custom {@link #toString()} method
 *
 */
public final class ApplicationEmojiWrapper extends ApplicationEmojiImpl {
    /**
     * Constructs a new ApplicationEmojiWrapper with the provided ApplicationEmoji
     *
     * @param   emoji   the ApplicationEmoji to wrap
     */
    public ApplicationEmojiWrapper(@NotNull ApplicationEmoji emoji) {
        super(emoji.getIdLong(), (JDAImpl) emoji.getJDA(), emoji.getOwner());
    }

    /**
     * Returns {@link #getAsMention the mention} of the emoji (e.g. {@code <:emoji_name:emoji_id>})
     *
     * @return the mention of the emoji
     */
    @Override @NotNull
    public String toString() {
        return getAsMention();
    }
}
