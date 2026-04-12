package xyz.srnyx.lazylibrary.emoji;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import net.dv8tion.jda.api.managers.ApplicationEmojiManager;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.data.DataObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A wrapper for {@link ApplicationEmoji} that adds a custom {@link #toString()} method
 *
 * @param   emoji   the emoji to wrap
 */
public record ApplicationEmojiWrapper(@NotNull ApplicationEmoji emoji) implements ApplicationEmoji {
    /**
     * Returns the mention of the emoji (e.g. {@code <:emoji_name:emoji_id>})
     *
     * @return  the mention of the emoji
     */
    @Override @NotNull
    public String toString() {
        return emoji.getAsMention();
    }

    @Override @NotNull
    public JDA getJDA() {
        return emoji.getJDA();
    }
    @Override @Nullable
    public User getOwner() {
        return emoji.getOwner();
    }
    @Override @NotNull
    public RestAction<Void> delete() {
        return emoji.delete();
    }
    @Override @NotNull
    public ApplicationEmojiManager getManager() {
        return emoji.getManager();
    }
    @Override
    public boolean isAnimated() {
        return emoji.isAnimated();
    }
    @Override
    public long getIdLong() {
        return emoji.getIdLong();
    }
    @Override @NotNull
    public String getName() {
        return emoji.getName();
    }
    @Override @NotNull
    public String getAsReactionCode() {
        return emoji.getAsReactionCode();
    }
    @Override @NotNull
    public DataObject toData() {
        return emoji.toData();
    }
}
