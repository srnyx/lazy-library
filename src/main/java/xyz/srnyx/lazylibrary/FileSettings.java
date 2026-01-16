package xyz.srnyx.lazylibrary;

import io.github.freya022.botcommands.api.commands.text.annotations.RequireOwner;

import net.dv8tion.jda.api.JDABuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;

import xyz.srnyx.javautilities.parents.Stringable;
import xyz.srnyx.lazylibrary.services.DatabaseSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * A class to hold the settings defined in the settings file
 */
public class FileSettings extends Stringable {
    /**
     * The file to load settings from
     */
    @NotNull public final LazyFile file;

    /**
     * The bot token, used for {@link JDABuilder}
     */
    @Nullable public final String token;
    /**
     * The database connection URL, used for {@link DatabaseSource}
     */
    @Nullable public final String database;
    /**
     * The owners settings, used for {@link RequireOwner}
     */
    @NotNull public final Owners owners;
    /**
     * The Pterodactyl settings, used for Pterodactyl power integration
     */
    @Nullable public final Pterodactyl pterodactyl;

    /**
     * Constructor to load the settings from the settings file
     *
     * @param   fileName    the name of the settings file
     */
    public FileSettings(@NotNull String fileName) {
        file = new LazyFile(fileName, NodeStyle.BLOCK, true);
        token = file.yaml.node("token").getString();
        database = file.yaml.node("database").getString();
        owners = new Owners();

        // pterodactyl
        Pterodactyl newPterodactyl = null;
        try {
            newPterodactyl = new Pterodactyl();
        } catch (final NullPointerException _) {
            LazyLibrary.LOGGER.warn("Missing settings for Pterodactyl, integration disabled!");
        }
        pterodactyl = newPterodactyl;
    }

    /**
     * A class to hold the Pterodactyl settings
     */
    public class Pterodactyl {
        /**
         * The Pterodactyl panel URL, used for Pterodactyl API requests
         */
        @NotNull public final String panelUrl;
        /**
         * The Pterodactyl API key, used for Pterodactyl API requests
         */
        @NotNull public final String apiKey;
        /**
         * The Pterodactyl server ID that the bot is running on, used for Pterodactyl API requests
         */
        @NotNull public final String serverId;

        /**
         * Constructor to load the Pterodactyl settings from the settings file
         *
         * @throws  NullPointerException    if any required settings are missing
         */
        public Pterodactyl() throws NullPointerException {
            final ConfigurationNode node = file.yaml.node("pterodactyl");
            panelUrl = Objects.requireNonNull(node.node("panel-url").getString());
            apiKey = Objects.requireNonNull(node.node("api-key").getString());
            serverId = Objects.requireNonNull(node.node("server-id").getString());
        }
    }

    /**
     * A class to hold the owners settings
     */
    public class Owners {
        /**
         * Primary owner ID, used for {@link RequireOwner} and to get errors DMed
         */
        @Nullable public final Long primary;
        /**
         * Set of other owner IDs, used for {@link RequireOwner}
         */
        @NotNull public final Set<Long> other;

        /**
         * Constructor to load the owners from the settings file
         */
        public Owners() {
            final ConfigurationNode node = file.yaml.node("owners");
            // primary
            final ConfigurationNode primaryNode = node.node("primary");
            primary = primaryNode.virtual() ? null : primaryNode.getLong();
            // other
            Set<Long> newOther = new HashSet<>();
            try {
                newOther = new HashSet<>(node.node("other").getList(Long.class, new ArrayList<>()));
            } catch (final SerializationException e) {
                e.printStackTrace();
            }
            other = newOther;
        }
    }
}
