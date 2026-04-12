package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.JDAService;
import io.github.freya022.botcommands.api.core.annotations.BEventListener;
import io.github.freya022.botcommands.api.core.events.BReadyEvent;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.Set;


/**
 * A service that creates and configures a JDA instance for the bot.
 * It uses the settings provided in the LazyLibrary instance to determine the gateway intents, cache flags, and token for the bot.
 * The JDA instance is created when the bot is ready, and a log message is printed once the bot has finished starting.
 */
@BService
public class JDACreator extends JDAService {
    @NotNull private final LazyLibrary library;

    /**
     * Constructs a new JDACreator using the provided LazyLibrary settings.
     *
     * @param   library the LazyLibrary instance containing JDA configuration
     */
    public JDACreator(@NotNull LazyLibrary library) {
        this.library = library;
    }

    @Override @NotNull
    public Set<GatewayIntent> getIntents() {
        return library.gatewayIntents;
    }

    @Override @NotNull
    public Set<CacheFlag> getCacheFlags() {
        return library.cacheFlags;
    }

    @Override
    public void createJDA(@NotNull BReadyEvent event, @NotNull IEventManager eventManager) {
        if (library.fileSettings.token == null) throw new IllegalStateException("Token is not set in the settings file");
        final JDABuilder builder = create(library.fileSettings.token);
        library.jdaBuilder.accept(builder);
        builder.build();
    }

    /**
     * Logs a message when the bot has finished starting.
     *
     * @param   event   the ReadyEvent triggered when the bot is ready
     */
    @BEventListener
    public void onReady(@NotNull ReadyEvent event) {
        LazyLibrary.LOGGER.info("{} has finished starting!", library.botClass.getSimpleName());
    }
}
