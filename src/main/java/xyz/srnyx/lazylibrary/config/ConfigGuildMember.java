package xyz.srnyx.lazylibrary.config;

import net.dv8tion.jda.api.entities.Guild;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import java.util.function.Supplier;


/**
 * A class to hold a {@link Supplier} for the {@link Guild}
 * <br>Contains useful utility methods
 */
public class ConfigGuildMember extends ConfigObject {
    /**
     * The {@link Supplier} for the {@link Guild} that owns the object
     */
    @NotNull public final Supplier<Guild> guildSupplier;

    /**
     * Creates a new {@link ConfigGuildMember}
     *
     * @param   guildSupplier   {@link #guildSupplier}
     */
    public ConfigGuildMember(@NotNull Supplier<Guild> guildSupplier) {
        this.guildSupplier = guildSupplier;
    }

    /**
     * Creates a new {@link ConfigGuildMember}
     *
     * @param   guildSupplier   {@link #guildSupplier}
     * @param   node            a {@link ConfigurationNode} containing the ID
     */
    public ConfigGuildMember(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        super(node);
        this.guildSupplier = guildSupplier;
    }
}
