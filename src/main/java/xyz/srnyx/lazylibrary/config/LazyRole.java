package xyz.srnyx.lazylibrary.config;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import xyz.srnyx.javautilities.MiscUtility;
import xyz.srnyx.javautilities.parents.Stringable;

import xyz.srnyx.lazylibrary.LazyEmbed;
import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.Optional;
import java.util.function.Supplier;


public class LazyRole extends Stringable {
    @NotNull public final LazyLibrary bot;
    @NotNull public final Supplier<Guild> guildSupplier;
    public final long id;

    public LazyRole(@NotNull LazyLibrary bot, @NotNull Supplier<Guild> guildSupplier, long id) {
        this.bot = bot;
        this.guildSupplier = guildSupplier;
        this.id = id;
    }

    public LazyRole(@NotNull LazyLibrary bot, @NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        this.bot = bot;
        this.guildSupplier = guildSupplier;
        this.id = node.getLong();
    }

    @NotNull
    public String getMention() {
        return "<@&" + id + ">";
    }

    @NotNull
    public Optional<Role> getRole() {
        final Guild guild = guildSupplier.get();
        return guild == null ? Optional.empty() : getRole(guild);
    }

    @NotNull
    public Optional<Role> getRole(@NotNull Guild guild) {
        return Optional.ofNullable(guild.getRoleById(id));
    }

    public boolean hasRole(@NotNull Member member) {
        for (final Role role : member.getRoles()) if (role.getIdLong() == id) return true;
        return false;
    }

    public boolean hasRole(@NotNull Guild guild, long userId) {
        return MiscUtility.handleException(() -> guild.retrieveMemberById(userId).complete())
                .filter(this::hasRole)
                .isPresent();
    }

    public boolean hasRole(long userId) {
        final Guild guild = guildSupplier.get();
        return guild != null && hasRole(guild, userId);
    }

    public boolean checkDontHaveRole(@NotNull GuildSlashEvent event) {
        final boolean hasRole = hasRole(event.getMember());
        if (!hasRole) event.replyEmbeds(LazyEmbed.noPermission(getMention()).build(bot)).setEphemeral(true).queue();
        return !hasRole;
    }
}
