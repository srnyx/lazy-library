package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.jetbrains.annotations.NotNull;

import java.util.List;


@BService
public class BotStopper {
    @NotNull protected final List<BotStopListener> listeners;

    public BotStopper(@NotNull List<BotStopListener> listeners) {
        this.listeners = listeners;
    }

    public void stop() {
        for (final BotStopListener listener : listeners) listener.onStop();
        System.exit(0);
    }
}
