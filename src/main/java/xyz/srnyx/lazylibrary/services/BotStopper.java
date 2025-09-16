package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.BContext;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.events.BotStopEvent;


@BService
public class BotStopper {
    @NotNull private final BContext context;

    public BotStopper(@NotNull BContext context) {
        this.context = context;
    }

    public void stop() {
        context.getEventDispatcher().dispatchEvent(new BotStopEvent(context));
        System.exit(0);
    }
}
