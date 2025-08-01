package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.events.GuildMemberStartBoostEvent;
import xyz.srnyx.lazylibrary.events.GuildMemberStopBoostEvent;
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
     * <b>OVERRIDING THIS WILL BREAK {@link GuildVoiceJoinEvent} AND {@link GuildVoiceLeaveEvent}</b>
     * <br>If you need to override this, make sure to call {@code super.onGuildVoiceUpdate(event)} in your method!
     */
    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        // Joined
        if (event.getChannelJoined() != null) onGuildVoiceJoin(new GuildVoiceJoinEvent(event));
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

    /**
     * <b>OVERRIDING THIS WILL BREAK {@link GuildMemberStartBoostEvent} AND {@link GuildMemberStopBoostEvent}</b>
     * <br>If you need to override this, make sure to call {@code super.onGuildMemberUpdateBoostTime(event)} in your method!
     */
    @Override
    public void onGuildMemberUpdateBoostTime(@NotNull GuildMemberUpdateBoostTimeEvent event) {
        // Stopped
        if (event.getNewValue() == null) {
            onGuildMemberStopBoost(new GuildMemberStopBoostEvent(event));
            return;
        }
        // Started
        onGuildMemberStartBoost(new GuildMemberStartBoostEvent(event));
    }

    /**
     * Called when a member starts boosting a guild
     *
     * @param   event   the {@link GuildMemberStartBoostEvent} that was fired
     */
    public void onGuildMemberStartBoost(@NotNull GuildMemberStartBoostEvent event) {
        // This should be overridden
    }

    /**
     * Called when a member stops boosting a guild
     *
     * @param   event   the {@link GuildMemberStopBoostEvent} that was fired
     */
    public void onGuildMemberStopBoost(@NotNull GuildMemberStopBoostEvent event) {
        // This should be overridden
    }
}
