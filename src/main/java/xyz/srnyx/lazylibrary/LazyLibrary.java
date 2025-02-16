package xyz.srnyx.lazylibrary;

import com.freya02.botcommands.api.CommandsBuilder;
import com.freya02.botcommands.api.components.DefaultComponentManager;

import com.zaxxer.hikari.HikariDataSource;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.srnyx.lazylibrary.settings.ApplicationDependency;
import xyz.srnyx.lazylibrary.settings.LazySettings;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.Supplier;


/**
 * The main class for the bot
 */
@SuppressWarnings("EmptyMethod")
public class LazyLibrary {
    /**
     * The {@link Logger} instance
     */
    @NotNull public static Logger LOGGER = LoggerFactory.getLogger("LazyLibrary");

    /**
     * The {@link LazySettings settings} for the bot
     */
    @NotNull public final LazySettings settings = new LazySettings(this);
    /**
     * The {@link JDA} instance
     */
    public JDA jda;

    /**
     * Starts the bot
     */
    public LazyLibrary() {
        settings.dependencies(new ApplicationDependency<>((Class<? super LazyLibrary>) getClass(), () -> this));
        setSettings();
        LOGGER = LoggerFactory.getLogger(settings.loggerName);
        onStart();

        // Start bot
        try {
            final JDABuilder builder = JDABuilder.create(settings.gatewayIntents).setToken(settings.fileSettings.token);
            if (settings.jdaBuilder != null) settings.jdaBuilder.accept(builder);
            jda = builder.build().awaitReady();
        } catch (final InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            System.exit(0);
            return;
        }
        onReady();

        // BotCommands
        final CommandsBuilder builder = CommandsBuilder.newBuilder()
                .textCommandBuilder(textCommands -> textCommands.disableHelpCommand(true));
        // Owners, search paths, and command dependency
        if (settings.fileSettings.ownersPrimary != null) builder.addOwners(settings.fileSettings.ownersPrimary);
        settings.fileSettings.ownersOther.forEach(builder::addOwners);
        settings.searchPaths.forEach(builder::addSearchPath);
        builder.extensionsBuilder(extensionsBuilder -> settings.dependencies.forEach(dependency -> extensionsBuilder.registerCommandDependency((Class<Object>) dependency.clazz(), (Supplier<Object>) dependency.supplier())));
        // Database
        if (settings.fileSettings.database != null) {
            //noinspection resource
            final HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(settings.fileSettings.database);
            dataSource.setMaximumPoolSize(3);
            dataSource.setLeakDetectionThreshold(5000);
            try {
                builder.setComponentManager(new DefaultComponentManager(() -> {
                    try {
                        return dataSource.getConnection();
                    } catch (final SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }));
            } catch (final RuntimeException e) {
                e.printStackTrace();
            }
        }
        // Build
        settings.builder.accept(builder);
        builder.build(jda);

        // stop command
        new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (settings.defaultStopCommand && line.equals("stop")) {
                    stopBot();
                    return;
                }
                onConsoleCommand(new ConsoleCommand(line));
            }
        }).start();
    }

    /**
     * Returns the name of the settings file (excluding {@code .yml})
     *
     * @return  the name of the settings file (excluding {@code .yml})
     */
    @NotNull
    public String getSettingsFileName() {
        return "config";
    }

    /**
     * Set the {@link #settings} for the bot here by overriding this method
     */
    public void setSettings() {
        // Should be overridden
    }

    /**
     * Called when the instance is created (after {@link #settings} and {@link #LOGGER} are set)
     */
    public void onStart() {
        // Should be overridden
    }

    /**
     * Called when the {@link JDA} is ready
     */
    public void onReady() {
        // Should be overridden
    }

    /**
     * Called when the stop command is executed
     */
    public void onStop() {
        // Should be overridden
    }

    /**
     * Stops the bot (calls {@link #onStop()} and exits the program)
     */
    public void stopBot() {
        onStop();
        System.exit(0);
    }

    /**
     * Called when a command is sent in the console
     *
     * @param   command the {@link ConsoleCommand} that was sent
     */
    public void onConsoleCommand(@NotNull ConsoleCommand command) {
        // Should be overridden
    }

    /**
     * Checks if the given {@link Long ID} is an owner
     *
     * @param   id  the {@link Long ID} to check
     *
     * @return      {@code true} if the given ID is an owner, {@code false} otherwise
     */
    public boolean isOwner(long id) {
        final Long ownersPrimary = settings.fileSettings.ownersPrimary;
        return (ownersPrimary != null && id == ownersPrimary) || (settings.fileSettings.ownersOther.contains(id));
    }
}
