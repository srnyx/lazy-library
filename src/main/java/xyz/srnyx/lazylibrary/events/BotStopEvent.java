package xyz.srnyx.lazylibrary.events;

import io.github.freya022.botcommands.api.core.BContext;
import io.github.freya022.botcommands.api.core.events.BEvent;

import org.jetbrains.annotations.NotNull;


public class BotStopEvent extends BEvent {
    public BotStopEvent(@NotNull BContext context) {
        super(context);
    }
}
