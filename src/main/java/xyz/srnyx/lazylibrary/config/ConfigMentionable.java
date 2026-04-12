package xyz.srnyx.lazylibrary.config;

import net.dv8tion.jda.api.entities.Guild;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import java.util.function.Supplier;


/**
 * A class to hold an ID and a {@link Supplier} for the {@link Guild}
 * <br>Contains useful utility methods
 */
public abstract class ConfigMentionable extends ConfigGuildMember {
    /**
     * The ID of the object
     */
    public final long id;

    /**
     * Creates a new {@link ConfigMentionable}
     *
     * @param   guildSupplier   {@link #guildSupplier}
     * @param   id              {@link #id}
     */
    public ConfigMentionable(@NotNull Supplier<Guild> guildSupplier, long id) {
        super(guildSupplier);
        this.id = id;
    }

    /**
     * Creates a new {@link ConfigMentionable}
     *
     * @param   guildSupplier   {@link #guildSupplier}
     * @param   node            a {@link ConfigurationNode} containing the ID
     */
    public ConfigMentionable(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        super(guildSupplier, node);
        this.id = node.getLong();
    }

    /**
     * Gets the mention of the object
     *
     * @return  the mention of the object
     */
    @NotNull
    public abstract String getMention();
}
