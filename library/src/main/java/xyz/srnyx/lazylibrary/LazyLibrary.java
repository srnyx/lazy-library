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
     * The {@link Logger} used for the bot
     */
    @NotNull public static Logger LOGGER = LoggerFactory.getLogger("LazyLibrary");
    /**
     * The {@link LazySettings settings} for the bot
     */
    public static LazySettings SETTINGS;

    /**
     * The {@link JDA} instance
     */
    public JDA jda;

    /**
     * Starts the bot
     *
     * @param   settingsFileName    the name of the settings file (without {@code .yml}), or {@code null} to use the default
     */
    public LazyLibrary(@Nullable String settingsFileName) {
        SETTINGS = settingsFileName == null ? new LazySettings() : new LazySettings(settingsFileName);
        onStart();
        LOGGER = LoggerFactory.getLogger(SETTINGS.loggerName);

        // Start bot
        try {
            jda = JDABuilder.create(SETTINGS.gatewayIntents).disableCache(SETTINGS.disabledCacheFlags).setToken(SETTINGS.token).build().awaitReady();
        } catch (final InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            System.exit(0);
            return;
        }
        onReady();

        // BotCommands
        final CommandsBuilder builder = (SETTINGS.ownersPrimary == null ? CommandsBuilder.newBuilder() : CommandsBuilder.newBuilder(SETTINGS.ownersPrimary))
                .textCommandBuilder(textCommands -> textCommands.disableHelpCommand(true));
        // Owners, search paths, and command dependency
        if (SETTINGS.ownersOther != null) SETTINGS.ownersOther.forEach(builder::addOwners);
        for (final String path : SETTINGS.searchPaths) builder.addSearchPath(path);
        if (SETTINGS.extensionsBuilder != null) builder.extensionsBuilder(SETTINGS.extensionsBuilder);
        // Database
        if (SETTINGS.database != null) {
            //noinspection resource
            final HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(SETTINGS.database);
            dataSource.setMaximumPoolSize(3);
            dataSource.setLeakDetectionThreshold(5000);
            builder.setComponentManager(new DefaultComponentManager(() -> {
                try {
                    return dataSource.getConnection();
                } catch (final SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }));
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
