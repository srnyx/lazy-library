package xyz.srnyx.lazylibrary.services.stopper;

import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;


@InterfacedService(acceptMultiple = true)
public interface BotStopListener {
    void onStop();
}
