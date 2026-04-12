package xyz.srnyx.lazylibrary.services.consolecommand;

import io.github.freya022.botcommands.api.core.BContext;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;
import xyz.srnyx.lazylibrary.services.power.BotPower;

import java.util.Scanner;


/**
 * A service that scans the console for commands and passes them to {@link ConsoleCommandHandler}s, also handles the default "stop" command if enabled in the library
 */
@BService
public class ConsoleCommandScanner {
    /**
     * Creates the service and starts the console command scanning thread
     *
     * @param   context the {@link BContext} instance to get services from
     * @param   library the {@link LazyLibrary} instance to check for default stop command
     * @param   power   the {@link BotPower} instance to stop the bot if the default stop command is enabled and used
     */
    public ConsoleCommandScanner(@NotNull BContext context, @NotNull LazyLibrary library, @NotNull BotPower power) {
        new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                final ConsoleCommand command = new ConsoleCommand(scanner.nextLine());
                if (library.defaultStopCommand && command.getRaw().equals("stop")) {
                    power.stop();
                    return;
                }
                for (final ConsoleCommandHandler handler : context.getServiceContainer().getInterfacedServices(ConsoleCommandHandler.class)) handler.handleCommand(command);
            }
        }).start();
    }
}
