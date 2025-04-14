package xyz.srnyx.lazylibrary.config;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;

import xyz.srnyx.javautilities.parents.Stringable;

import java.util.Optional;
import java.util.function.Supplier;


/**
 * A class to hold a channel ID and a {@link Supplier} for the {@link Guild}
 * <br>Contains useful utility methods
 *
 * @param   <T> the type of channel
 */
public class LazyChannel<T extends Channel> extends Stringable {
    /**
     * The {@link Supplier} for the {@link Guild} that owns the channel
     */
    @NotNull public final Supplier<Guild> guildSupplier;
    /**
     * The ID of the channel
     */
    public final long id;

    /**
     * Creates a new {@link LazyChannel}
     *
     * @param   guildSupplier   {@link #guildSupplier}
     * @param   id              {@link #id}
     */
    public LazyChannel(@NotNull Supplier<Guild> guildSupplier, long id) {
        this.guildSupplier = guildSupplier;
        this.id = id;
    }

    /**
     * Creates a new {@link LazyChannel}
     *
     * @param   guildSupplier   {@link #guildSupplier}
     * @param   node            a {@link ConfigurationNode} containing the channel ID
     */
    public LazyChannel(@NotNull Supplier<Guild> guildSupplier, @NotNull ConfigurationNode node) {
        this.guildSupplier = guildSupplier;
        this.id = node.getLong();
    }

    /**
     * Gets the mention of the channel
     *
     * @return  the mention of the channel
     */
    @NotNull
    public String getMention() {
        return "<#" + id + ">";
    }

    /**
     * Gets the {@link Channel} from the {@link #guildSupplier}
     *
     * @return  the {@link Channel} from the {@link #guildSupplier}
     */
    @NotNull
    public Optional<T> getChannel() {
        final Guild guild = guildSupplier.get();
        return guild == null ? Optional.empty() : getChannel(guild);
    }

    /**
     * Gets the {@link Channel} from the given {@link Guild}
     * <br>This skips retrieving the {@link Guild} from {@link #guildSupplier} (for performance)
     *
     * @param   guild   the {@link Guild} to get the {@link Channel} from (should match {@link #guildSupplier})
     *
     * @return          the {@link Channel} from the given {@link Guild}
     */
    @NotNull
    public Optional<T> getChannel(@NotNull Guild guild) {
        return Optional.ofNullable((T) guild.getGuildChannelById(id));
    }
}
