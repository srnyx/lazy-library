package xyz.srnyx.lazylibrary.services.consolecommand;

import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;

import org.jetbrains.annotations.NotNull;


/**
 * A service to handle console commands, used by {@link ConsoleCommandScanner}
 */
@InterfacedService(acceptMultiple = true)
public interface ConsoleCommandHandler {
    /**
     * Handles a console command
     *
     * @param   command the console command to handle
     */
    void handleCommand(@NotNull ConsoleCommand command);
}
