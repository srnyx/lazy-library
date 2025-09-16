package xyz.srnyx.lazylibrary.events;

import io.github.freya022.botcommands.api.core.BContext;
import io.github.freya022.botcommands.api.core.annotations.BEventListener;
import io.github.freya022.botcommands.api.core.events.BEvent;

import org.jetbrains.annotations.NotNull;


/**
 * Event called when the bot is stopping
 * <br>{@link BEventListener#mode()} needs to be {@link BEventListener.RunMode#BLOCKING RunMode.BLOCKING}
 */
public class BotStopEvent extends BEvent {
    public BotStopEvent(@NotNull BContext context) {
        super(context);
    }
}
