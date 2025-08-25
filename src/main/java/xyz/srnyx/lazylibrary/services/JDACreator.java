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


@BService
public class JDACreator extends JDAService {
    @NotNull private final LazyLibrary settings;

    public JDACreator(@NotNull LazyLibrary settings) {
        this.settings = settings;
    }

    @Override @NotNull
    public Set<GatewayIntent> getIntents() {
        return settings.gatewayIntents;
    }

    @Override @NotNull
    public Set<CacheFlag> getCacheFlags() {
        return settings.cacheFlags;
    }

    @Override
    public void createJDA(@NotNull BReadyEvent event, @NotNull IEventManager eventManager) {
        if (settings.fileSettings.token == null) throw new IllegalStateException("Token is not set in the settings file");
        final JDABuilder builder = create(settings.fileSettings.token);
        if (settings.jdaBuilder != null) settings.jdaBuilder.accept(builder);
        builder.build();
    }

    @BEventListener
    public void onReady(@NotNull ReadyEvent event) {
        LazyLibrary.LOGGER.info("{} has finished starting!", settings.botClass.getSimpleName());
    }
}
