package xyz.srnyx.lazylibrary.config;

import io.github.freya022.botcommands.api.commands.application.slash.GuildSlashEvent;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import xyz.srnyx.javautilities.MiscUtility;
import xyz.srnyx.javautilities.parents.Stringable;

import xyz.srnyx.lazylibrary.LazyEmbed;

import java.util.Optional;
import java.util.function.Supplier;


/**
 * A class to hold a role ID and a {@link Supplier} for the {@link Guild}
 * <br>Contains useful utility methods
 */
public class LazyRole extends Stringable {
    /**
     * The {@link Supplier} for the {@link Guild} that owns the role
     */
    @NotNull public final Supplier<Guild> guildSupplier;
    /**
     * The ID of the role
     */
    public final long id;

    public LazyRole(@NotNull Supplier<Guild> guildSupplier, long id) {
        this.guildSupplier = guildSupplier;
        this.id = id;
    }

    public LazyRole(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        this.guildSupplier = guildSupplier;
        this.id = node.getLong();
    }

    /**
     * Gets the mention of the role
     *
     * @return  the mention of the role
     */
    @NotNull
    public String getMention() {
        return "<@&" + id + ">";
    }

    /**
     * Gets the {@link Role} from the {@link #guildSupplier}
     *
     * @return  the {@link Role} from the {@link #guildSupplier}
     */
    @NotNull
    public Optional<Role> getRole() {
        final Guild guild = guildSupplier.get();
        return guild == null ? Optional.empty() : getRole(guild);
    }

    /**
     * Gets the {@link Role} from the given {@link Guild}
     * <br>This skips retrieving the {@link Guild} from {@link #guildSupplier} (for performance)
     *
     * @param   guild   the {@link Guild} to get the {@link Role} from (should match {@link #guildSupplier})
     *
     * @return          the {@link Role} from the given {@link Guild}
     */
    @NotNull
    public Optional<Role> getRole(@NotNull Guild guild) {
        return Optional.ofNullable(guild.getRoleById(id));
    }

    /**
     * Checks if the given {@link Member} has the role
     *
     * @param   member  the {@link Member} to check
     *
     * @return          {@code true} if the given {@link Member} has the role, {@code false} otherwise
     */
    public boolean hasRole(@NotNull Member member) {
        for (final Role role : member.getRoles()) if (role.getIdLong() == id) return true;
        return false;
    }

    /**
     * Checks if the given user ID has the role
     *
     * @param   guild   the {@link Guild} to retrieve the {@link Member} and {@link Role} from (should match {@link #guildSupplier})
     * @param   userId  the user ID to check
     *
     * @return          {@code true} if the given user ID has the role, {@code false} if the user isn't in the guild or doesn't have the role
     */
    public boolean hasRole(@NotNull Guild guild, long userId) {
        return MiscUtility.handleException(() -> guild.retrieveMemberById(userId).complete())
                .filter(this::hasRole)
                .isPresent();
    }

    /**
     * Checks if the given user ID has the role
     *
     * @param   userId  the user ID to check
     *
     * @return          {@code true} if the given user ID has the role, {@code false} if the user isn't in the guild or doesn't have the role
     */
    public boolean hasRole(long userId) {
        final Guild guild = guildSupplier.get();
        return guild != null && hasRole(guild, userId);
    }

    /**
     * Checks if the user who ran the {@link GuildSlashEvent} has the role and replies with an error message if not
     *
     * @param   event   the {@link GuildSlashEvent} to get the user from and reply to (if needed)
     *
     * @return          {@code true} if the user <b>doesn't</b> have the role, {@code false} if they <b>do</b>
     */
    public boolean checkDontHaveRole(@NotNull IReplyCallback event) {
        final Member member = event.getMember();
        final boolean hasRole = member != null ? hasRole(member) : hasRole(event.getUser().getIdLong());
        if (!hasRole) event.replyEmbeds(LazyEmbed.noPermission(getMention()).build()).setEphemeral(true).queue();
        return !hasRole;
    }
}
