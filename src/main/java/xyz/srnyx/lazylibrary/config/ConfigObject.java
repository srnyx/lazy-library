package xyz.srnyx.lazylibrary.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;


/**
 * A class to hold a {@link ConfigurationNode} key as a string
 * <br>Contains useful utility methods
 */
public class ConfigObject {
    /**
     * The key of the {@link ConfigurationNode} as a string
     */
    @Nullable public final String node;

    /**
     * Creates a new {@link ConfigObject} with a null node
     */
    public ConfigObject() {
        this.node = null;
    }

    /**
     * Creates a new {@link ConfigObject} with the key of the given {@link ConfigurationNode} as a string
     *
     * @param   node    the {@link ConfigurationNode} to get the key from
     */
    public ConfigObject(@NotNull ConfigurationNode node) {
        final Object key = node.key();
        this.node = key != null ? key.toString() : null;
    }
}
