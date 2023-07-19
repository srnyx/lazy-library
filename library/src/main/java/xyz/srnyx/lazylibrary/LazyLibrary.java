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
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * The main class for the bot
 */
@SuppressWarnings("EmptyMethod")
public class LazyLibrary {
    public static Logger LOGGER;

    /**
     * The {@link LazySettings settings} for the bot
     */
    @NotNull public final LazySettings settings = new LazySettings(getSettingsFileName());
    /**
     * The {@link JDA} instance
     */
    public JDA jda;

    /**
     * Starts the bot
     */
    public LazyLibrary() {
        settings.dependencies(new ApplicationDependency<>((Class<? super LazyLibrary>) getClass(), () -> this));
        getSettings().accept(settings);
        LOGGER = LoggerFactory.getLogger(settings.loggerName);
        onStart();

        // Start bot
        try {
            jda = JDABuilder.create(settings.gatewayIntents).disableCache(settings.disabledCacheFlags).setToken(settings.fileSettings.token).build().awaitReady();
        } catch (final InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            System.exit(0);
            return;
        }
        onReady();

        // BotCommands
        final CommandsBuilder builder = (settings.fileSettings.ownersPrimary == null ? CommandsBuilder.newBuilder() : CommandsBuilder.newBuilder(settings.fileSettings.ownersPrimary))
                .textCommandBuilder(textCommands -> textCommands.disableHelpCommand(true));
        // Owners, search paths, and command dependency
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
                if (!scanner.nextLine().equals("stop")) continue;
                onStop();
                System.exit(0);
            }
        }).start();
    }

    @NotNull
    public String getSettingsFileName() {
        return "config";
    }

    @NotNull
    public Consumer<LazySettings> getSettings() {
        return settingsConsumer -> {};
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
