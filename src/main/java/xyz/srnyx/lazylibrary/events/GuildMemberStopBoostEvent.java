package xyz.srnyx.lazylibrary.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;

import org.jetbrains.annotations.NotNull;


/**
 * Fired when a member stops boosting a guild
 */
public class GuildMemberStopBoostEvent extends GenericGuildMemberEvent {
    /**
     * Constructs a new {@link GuildMemberStartBoostEvent}
     *
     * @param   api             the {@link JDA} instance
     * @param   responseNumber  the response number
     * @param   member          the {@link Member member} who started boosting
     */
    public GuildMemberStopBoostEvent(@NotNull JDA api, long responseNumber, @NotNull Member member) {
        super(api, responseNumber, member);
    }

    /**
     * Constructs a new {@link GuildMemberStartBoostEvent} from a {@link GuildMemberUpdateBoostTimeEvent}
     *
     * @param   event   the {@link GuildMemberUpdateBoostTimeEvent} to construct from
     */
    public GuildMemberStopBoostEvent(@NotNull GuildMemberUpdateBoostTimeEvent event) {
        this(event.getJDA(), event.getResponseNumber(), event.getMember());
    }
}
