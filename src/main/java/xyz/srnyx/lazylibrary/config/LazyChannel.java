package xyz.srnyx.lazylibrary.config;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import xyz.srnyx.javautilities.parents.Stringable;

import java.util.Optional;
import java.util.function.Supplier;


public class LazyChannel<T extends Channel> extends Stringable {
    @NotNull public final Supplier<Guild> guildSupplier;
    public final long id;

    public LazyChannel(@NotNull Supplier<Guild> guildSupplier, long id) {
        this.guildSupplier = guildSupplier;
        this.id = id;
    }

    public LazyChannel(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        this.guildSupplier = guildSupplier;
        this.id = node.getLong();
    }

    @NotNull
    public String getMention() {
        return "<#" + id + ">";
    }

    @NotNull
    public Optional<T> getChannel() {
        final Guild guild = guildSupplier.get();
        return guild == null ? Optional.empty() : getChannel(guild);
    }

    @NotNull
    public Optional<T> getChannel(@NotNull Guild guild) {
        return Optional.ofNullable((T) guild.getGuildChannelById(id));
    }
}
