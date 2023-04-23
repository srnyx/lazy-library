package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.events.GuildVoiceJoinEvent;
import xyz.srnyx.lazylibrary.events.GuildVoiceLeaveEvent;


/**
 * A modified {@link ListenerAdapter} that provides a few extra utilities and events
 */
@SuppressWarnings("EmptyMethod")
public class LazyListener extends ListenerAdapter {
    /**
     * The {@link LazyLibrary bot} instance
     */
    @NotNull protected final LazyLibrary lazyLibrary;

    /**
     * Creates a new {@link LazyListener}
     *
     * @param   lazyLibrary the {@link LazyLibrary bot} instance
     */
    public LazyListener(@NotNull LazyLibrary lazyLibrary) {
        this.lazyLibrary = lazyLibrary;
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        // Joined
        if (event.getChannelJoined() != null) {
            onGuildVoiceJoin(new GuildVoiceJoinEvent(event));
            return;
        }
        // Left
        if (event.getChannelLeft() != null) onGuildVoiceLeave(new GuildVoiceLeaveEvent(event));
    }

    /**
     * Called when a member joins a voice channel
     *
     * @param   event   the {@link GuildVoiceJoinEvent} that was fired
     */
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        // This should be overridden
    }

    /**
     * Called when a member leaves a voice channel
     *
     * @param   event   the {@link GuildVoiceLeaveEvent} that was fired
     */
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        // This should be overridden
    }
}
