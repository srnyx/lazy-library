package xyz.srnyx.lazylibrary.config;

import net.dv8tion.jda.api.entities.Guild;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import java.util.function.Supplier;


public abstract class ConfigMentionable extends ConfigGuildMember {
    /**
     * The ID of the object
     */
    public final long id;

    public ConfigMentionable(@NotNull Supplier<Guild> guildSupplier, long id) {
        super(guildSupplier);
        this.id = id;
    }

    public ConfigMentionable(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        super(guildSupplier, node);
        this.id = node.getLong();
    }

    @NotNull
    public abstract String getMention();
}
