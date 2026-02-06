package xyz.srnyx.lazylibrary.config;

import net.dv8tion.jda.api.entities.Guild;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import java.util.function.Supplier;


public class ConfigGuildMember extends ConfigObject {
    /**
     * The {@link Supplier} for the {@link Guild} that owns the object
     */
    @NotNull public final Supplier<Guild> guildSupplier;

    public ConfigGuildMember(@NotNull Supplier<Guild> guildSupplier) {
        this.guildSupplier = guildSupplier;
    }

    public ConfigGuildMember(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        super(node);
        this.guildSupplier = guildSupplier;
    }
}
