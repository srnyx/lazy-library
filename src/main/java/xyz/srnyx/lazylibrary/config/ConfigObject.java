package xyz.srnyx.lazylibrary.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;


public class ConfigObject {
    @Nullable public final String node;

    public ConfigObject() {
        this.node = null;
    }

    public ConfigObject(@NotNull ConfigurationNode node) {
        final Object key = node.key();
        this.node = key != null ? key.toString() : null;
    }
}
