package xyz.srnyx.lazylibrary.services.consolecommand;

import io.github.freya022.botcommands.api.core.BContext;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;
import xyz.srnyx.lazylibrary.services.BotStopper;

import java.util.Scanner;


@BService
public class ConsoleCommandScanner {
    public ConsoleCommandScanner(@NotNull BContext context, @NotNull LazyLibrary library, @NotNull BotStopper stopper) {
        new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                final ConsoleCommand command = new ConsoleCommand(scanner.nextLine());
                if (library.defaultStopCommand && command.getRaw().equals("stop")) {
                    stopper.stop();
                    return;
                }
                for (final ConsoleCommandHandler handler : context.getServiceContainer().getInterfacedServices(ConsoleCommandHandler.class)) handler.handleCommand(command);
            }
        }).start();
    }
}
