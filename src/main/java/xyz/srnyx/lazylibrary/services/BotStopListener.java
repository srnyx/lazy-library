package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.service.annotations.BService;
import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;


@BService @InterfacedService(acceptMultiple = true)
public interface BotStopListener {
    void onStop();
}
