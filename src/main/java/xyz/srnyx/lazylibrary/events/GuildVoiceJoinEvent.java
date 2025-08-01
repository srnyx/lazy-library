package xyz.srnyx.lazylibrary.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Event for handling when a {@link Member member} has joined an {@link AudioChannel audio channel}
 */
public class GuildVoiceJoinEvent extends GuildVoiceUpdateEvent {
    /**
     * Constructs a new {@link GuildVoiceJoinEvent}
     *
     * @param   api             the {@link JDA} instance
     * @param   responseNumber  the response number
     * @param   member          the {@link Member member} who joined the {@link AudioChannel audio channel}
     * @param   previous        the {@link AudioChannel audio channel} the {@link Member member} left
     */
    public GuildVoiceJoinEvent(@NotNull JDA api, long responseNumber, @NotNull Member member, @Nullable AudioChannel previous) {
        super(api, responseNumber, member, previous);
    }

    /**
     * Constructs a new {@link GuildVoiceJoinEvent} from a {@link GuildVoiceUpdateEvent}
     *
     * @param   event   the {@link GuildVoiceUpdateEvent} to construct from
     */
    public GuildVoiceJoinEvent(@NotNull GuildVoiceUpdateEvent event) {
        this(event.getJDA(), event.getResponseNumber(), event.getMember(), event.getChannelLeft());
    }

    @Override @NotNull
    public AudioChannelUnion getChannelJoined() {
        return Objects.requireNonNull(super.getChannelJoined());
    }

    @Override @NotNull
    public AudioChannel getNewValue() {
        return Objects.requireNonNull(super.getNewValue());
    }
}
