package xyz.srnyx.lazylibrary.services.power;

import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;


@InterfacedService(acceptMultiple = true)
public interface BotStopListener {
    void onStop();
}
