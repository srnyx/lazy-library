package xyz.srnyx.lazylibrary.services.power;

import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;


/**
 * A listener for when the bot is stopping, used to perform any necessary cleanup before the bot is killed
 */
@InterfacedService(acceptMultiple = true)
public interface BotStopListener {
    /**
     * Called when the bot is stopping, used to perform any necessary cleanup before the bot is killed
     */
    void onStop();
}
