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
public abstract class LazyListener extends ListenerAdapter {
    /**
     * {@link LazyListener}
     */
    public LazyListener() {
        // Exists to add a Javadoc
    }

    /**
     * The {@link LazyLibrary bot's} instance
     *
     * @return  the {@link LazyLibrary bot's} instance
     */
    @NotNull
    public abstract LazyLibrary getBot();

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
