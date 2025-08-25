package xyz.srnyx.lazylibrary.services.consolecommand;

import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;

import org.jetbrains.annotations.NotNull;


@InterfacedService(acceptMultiple = true)
public interface ConsoleCommandHandler {
    void handleCommand(@NotNull ConsoleCommand command);
}
