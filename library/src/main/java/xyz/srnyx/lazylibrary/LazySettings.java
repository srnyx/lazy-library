package xyz.srnyx.lazylibrary;

import com.freya02.botcommands.api.annotations.RequireOwner;
import com.freya02.botcommands.api.builder.ExtensionsBuilder;
import com.freya02.botcommands.api.components.ComponentManager;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;


/**
 * A class to hold settings for {@link LazyLibrary}
 */
@SuppressWarnings("EmptyMethod")
public class LazySettings {
    /**
     * The file to load settings from
     */
    @NotNull public final LazyFile file;

    // Settings from file
    /**
     * The bot token, used for {@link JDABuilder}
     */
    @Nullable public final String token;
    /**
     * The database connection URL, used for {@link ComponentManager}
     */
    @Nullable public final String database;
    /**
     * Primary owner ID, used for {@link RequireOwner} and to get errors DMed
     */
    @Nullable public final Long ownersPrimary;
    /**
     * Set of other owner IDs, used for {@link RequireOwner}
     */
    @Nullable public Set<Long> ownersOther = new HashSet<>();

    // Settings from code
    /**
     * The name of the logger to use
     */
    @NotNull public String loggerName = "LazyLibrary";
    /**
     * A set of {@link GatewayIntent gateway intents} to enable
     */
    @NotNull public final Set<GatewayIntent> gatewayIntents = new HashSet<>();
    /**
     * A set of {@link CacheFlag cache flags} to disable
     */
    @NotNull public final Set<CacheFlag> disabledCacheFlags = new HashSet<>();
    /**
     * A set of package paths to search for applications
     */
    @NotNull public final Set<String> searchPaths = new HashSet<>();
    /**
     * A {@link Consumer} for the {@link ExtensionsBuilder} for BotCommands
     */
    @Nullable public Consumer<ExtensionsBuilder> extensionsBuilder;

    /**
     * Creates a new LazySettings instance from a file
     *
     * @param   fileName    the name of the file to load settings from (without {@code .yml}), defaults to {@code config}
     */
    public LazySettings(@Nullable String fileName) {
        if (fileName == null) fileName = "config";
        file = new LazyFile(fileName, NodeStyle.BLOCK, true);
        token = file.yaml.node("token").getString();
        database = file.yaml.node("database").getString();

        // owners
        final ConfigurationNode ownersNode = file.yaml.node("owners");
        final ConfigurationNode ownersPrimaryNode = ownersNode.node("primary");
        ownersPrimary = ownersPrimaryNode.virtual() ? null : ownersPrimaryNode.getLong();
        try {
            ownersOther = new HashSet<>(ownersNode.node("other").getList(Long.class, new ArrayList<>()));
        } catch (final SerializationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new {@link LazySettings} instance from the {@code config.yml} file
     */
    public LazySettings() {
        this(null);
    }

    /**
     * Checks if the given {@link Long ID} is an owner
     *
     * @param   id  the {@link Long ID} to check
     *
     * @return      {@code true} if the given ID is an owner, {@code false} otherwise
     */
    public boolean isOwner(long id) {
        return (ownersPrimary != null && id == ownersPrimary) || (ownersOther != null && ownersOther.contains(id));
    }
}
