package xyz.srnyx.lazylibrary.settings;

import com.freya02.botcommands.api.annotations.RequireOwner;
import com.freya02.botcommands.api.components.ComponentManager;

import net.dv8tion.jda.api.JDABuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;

import xyz.srnyx.lazylibrary.LazyFile;
import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A class to hold the settings defined in the settings file
 */
public class FileSettings {
    /**
     * The file to load settings from
     */
    @NotNull public final LazyFile file;

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
    @NotNull public Set<Long> ownersOther = new HashSet<>();

    /**
     * Creates a new {@link FileSettings} for the given {@link LazyLibrary}
     *
     * @param   library the {@link LazyLibrary} to create the {@link FileSettings} for
     */
    public FileSettings(@NotNull LazyLibrary library) {
        file = new LazyFile(library, library.getSettingsFileName(), NodeStyle.BLOCK, true);
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
}
