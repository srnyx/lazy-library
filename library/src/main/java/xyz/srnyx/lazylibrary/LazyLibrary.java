package xyz.srnyx.lazylibrary;

import com.freya02.botcommands.api.CommandsBuilder;
import com.freya02.botcommands.api.components.DefaultComponentManager;

import com.zaxxer.hikari.HikariDataSource;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;


/**
 * The main class for the bot
 */
@SuppressWarnings("EmptyMethod")
public class LazyLibrary {
    /**
     * The {@link LazySettings settings} for the bot
     */
    @NotNull public final LazySettings settings;
    /**
     * The {@link JDA} instance
     */
    public JDA jda;
    /**
     * The {@link Logger} used for the bot
     */
    @NotNull public final Logger logger;

    /**
     * Starts the bot
     *
     * @param   settingsFileName    the name of the settings file (without {@code .yml}), or {@code null} to use the default
     */
    public LazyLibrary(@Nullable String settingsFileName) {
        settings = settingsFileName == null ? new LazySettings() : new LazySettings(settingsFileName);
        onStart();
        logger = LoggerFactory.getLogger(settings.loggerName);

        // Start bot
        try {
            jda = JDABuilder.create(settings.gatewayIntents).disableCache(settings.disabledCacheFlags).setToken(settings.token).build().awaitReady();
        } catch (final InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            System.exit(0);
            return;
        }
        onReady();

        // BotCommands
        final CommandsBuilder builder = (settings.ownersPrimary == null ? CommandsBuilder.newBuilder() : CommandsBuilder.newBuilder(settings.ownersPrimary))
                .textCommandBuilder(textCommands -> textCommands.disableHelpCommand(true));
        // Owners, search paths, and command dependency
        if (settings.ownersOther != null) settings.ownersOther.forEach(builder::addOwners);
        for (final String path : settings.searchPaths) builder.addSearchPath(path);
        if (settings.extensionsBuilder != null) builder.extensionsBuilder(settings.extensionsBuilder);
        // Database
        if (settings.database != null) {
            //noinspection resource
            final HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(settings.database);
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
        builder.build(jda);

        // stop command
        new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                if (!scanner.nextLine().equals("stop")) continue;
                onStop();
                System.exit(0);
            }
        }).start();
    }

    /**
     * Starts the bot with the default settings file name
     */
    public LazyLibrary() {
        this(null);
    }

    /**
     * Called when the instance is created
     * <p><b>This is where you should set most of the settings!</b>
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
}
