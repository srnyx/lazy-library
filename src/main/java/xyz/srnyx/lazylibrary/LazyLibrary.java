package xyz.srnyx.lazylibrary;

import io.github.freya022.botcommands.api.commands.application.provider.GlobalApplicationCommandManager;
import io.github.freya022.botcommands.api.commands.application.provider.GuildApplicationCommandManager;
import io.github.freya022.botcommands.api.core.BotCommands;
import io.github.freya022.botcommands.api.core.config.BConfigBuilder;
import io.github.freya022.botcommands.api.core.service.ServiceSupplier;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.messages.MessageRequest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.srnyx.javautilities.parents.Stringable;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;


/**
 * The main class of the LazyLibrary, responsible for initializing the bot and providing access to settings and configuration.
 * It is designed as a singleton, with the instance accessible via the {@link #INSTANCE} field.
 * <br><br>
 * <b>Initialization:</b>
 * To initialize the bot, call the {@link #startBot(Class) startBot} method with the main class of your bot as the argument. This will set up the necessary configurations and start the bot.
 * <br><br>
 * <b>Settings:</b>
 * The LazyLibrary uses a {@link FileSettings file-based settings system} to manage configuration. The settings file is loaded based on the value of {@link #fileSettingsName}, which defaults to "config". You can customize this name before starting the bot if needed.
 * <br><br>
 * <b>Logging:</b>
 * The library uses SLF4J for logging. By default, it logs using the class name of your bot, but you can customize this by setting the {@link #loggerName} before starting the bot.
 * <br><br>
 * <b>Gateway Intents and Cache Flags:</b>
 * You can specify additional gateway intents and cache flags that your bot requires by adding them to the {@link #gatewayIntents} and {@link #cacheFlags} sets before starting the bot.
 * <br><br>
 * <b>Custom Configuration:</b>
 * For more advanced configuration, you can use the {@link #builder} consumer to modify the BotCommands configuration directly before it is initialized.
 */
@BService
public class LazyLibrary extends Stringable {
    /**
     * The singleton instance of the LazyLibrary. This instance is initialized when the class is loaded and can be accessed from anywhere in the code using {@code LazyLibrary.INSTANCE}. It is designed to be a single point of access for the library's functionality and settings.
     */
    @NotNull public static final LazyLibrary INSTANCE = new LazyLibrary();
    /**
     * The logger for the library, which is initialized when the bot is started. By default, it uses the class name of the bot's main class for logging, but this can be customized by setting the {@link #loggerName} before starting the bot.
     */
    @NotNull public static Logger LOGGER = LoggerFactory.getLogger("LazyLibrary");

    /**
     * The main class of the bot, which is set when the {@link #startBot(Class) startBot} method is called. It is initialized when the bot is started and should not be modified directly.
     */
    public Class<?> botClass;
    /**
     * The name of the file settings for the bot. This is used to determine the name of the configuration file that will be loaded when the bot starts. By default, this is set to "config", which means the library will look for a file named "config.yml" in the working directory. You can change this name before starting the bot if you want to use a different configuration file.
     */
    @NotNull public String fileSettingsName = "config";
    /**
     * The file settings for the bot, which are loaded from a file based on the value of {@link #fileSettingsName}. This field is initialized when the bot is started and provides access to the configuration settings defined in the file. You can use this field to read and modify settings as needed throughout your bot's code.
     */
    public FileSettings fileSettings;
    /**
     * The name of the logger to use for the bot. If not set, the logger will default to using the class name of the bot's main class. You can set this to a custom name if you want to have a specific logger for your bot or if you want to integrate with an existing logging system.
     */
    @Nullable public String loggerName;
    /**
     * A set of package paths to search for commands, events, and other components. By default, the package of the main bot class is included in this set. You can add additional package paths to this set before starting the bot to have the library search those packages for components as well.
     */
    @NotNull public final Set<String> searchPaths = new HashSet<>();
    /**
     * A set of {@link GatewayIntent gateway intents} to enable for the JDA instance
     */
    @NotNull public Set<GatewayIntent> gatewayIntents = new HashSet<>();
    /**
     * A set of {@link CacheFlag cache flags} to enable for the JDA instance
     */
    @NotNull public Set<CacheFlag> cacheFlags = new HashSet<>();
    /**
     * A consumer that accepts a {@link JDABuilder} for configuring the JDA instance. This can be used to customize various aspects of the JDA configuration such as gateway intents, cache flags, and other settings before the JDA instance is built and the bot is started.
     * <p>
     * By default, this consumer does nothing, but you can set it to a custom implementation to apply your desired JDA configuration.
     */
    @NotNull public Consumer<JDABuilder> jdaBuilder = _ -> {};
    /**
     * A consumer that accepts a {@link BConfigBuilder} for configuring the BotCommands library. This can be used to customize various aspects of the library's behavior and features by modifying the configuration builder before the library is initialized.
     * <p>
     * By default, this consumer does nothing, but you can set it to a custom implementation to apply your desired configuration.
     */
    @NotNull public Consumer<BConfigBuilder> builder = _ -> {};
    /**
     * Whether to use the new components system (componentsV2) by default. This will make all components created with the library use the new system.
     * <p>
     * It is disabled by default, but can be enabled by setting this field to {@code true} before starting the bot.
     */
    public boolean defaultComponentsV2 = false;
    /**
     * Whether to register a default stop command that allows stopping the bot by typing "stop" in the console
     * <p>
     * It is enabled by default, but can be disabled by setting this field to {@code false} before starting the bot
     */
    public boolean defaultStopCommand = true;
    /**
     * Default values for {@link LazyEmbed embeds}. These values will be used in all embeds created with the library unless overridden. You can add default values for various embed properties such as title, description, color, etc. by using the {@link #embedDefault(LazyEmbed.Key, Object) embedDefault} method or by adding them directly to this map.
     */
    @NotNull public Map<LazyEmbed.Key, Object> embedDefaults = new EnumMap<>(LazyEmbed.Key.class);
    /**
     * A list of {@link Activity activities} to rotate between every few minutes
     * <br><i>Set to null to disable (default)</i>
     */
    @Nullable public List<Activity> activities = null;

    /**
     * Use {@link #INSTANCE} instead
     */
    private LazyLibrary() {
        searchPaths.add("xyz.srnyx.lazylibrary");
    }

    /**
     * Initializes the bot with the specified main class. This method sets up the logger, loads the file settings, creates necessary folders, and configures the BotCommands library with the provided settings and custom configuration.
     *
     * @param   botClass   the main class of the bot, used for logging and as a reference for loading commands
     */
    public void startBot(@NotNull Class<?> botClass) {
        this.botClass = botClass;

        // Set logger
        if (loggerName != null) {
            LOGGER = LoggerFactory.getLogger(loggerName);
        } else {
            LOGGER = LoggerFactory.getLogger(botClass);
        }

        // Get FileSettings
        fileSettings = new FileSettings(fileSettingsName);

        // Create command cache folder
        final File localShare = new File(".local/share");
        if (!localShare.exists() && !localShare.mkdirs()) LOGGER.warn("Failed to create .local/share folder");

        // Set default contexts
        GlobalApplicationCommandManager.Defaults.setContexts(InteractionContextType.ALL);
        GuildApplicationCommandManager.Defaults.setContexts(Collections.singleton(InteractionContextType.GUILD));

        // Set defaultComponentsV2
        MessageRequest.setDefaultUseComponentsV2(defaultComponentsV2);

        // Create BotCommands
        BotCommands.create(config -> {
            // LazySettings service
            config.services(services -> services.registerServiceSupplier(ServiceSupplier.builder(LazyLibrary.class).asPrimary().build(_ -> LazyLibrary.INSTANCE)));
            // Disable help text command
            config.textCommands(textCommands -> textCommands.disableHelp(true));
            // Owners
            if (fileSettings.owners.primary != null) config.addPredefinedOwners(fileSettings.owners.primary);
            fileSettings.owners.other.forEach(config::addPredefinedOwners);
            // Search paths
            config.addSearchPath(botClass.getPackage().getName());
            searchPaths.forEach(config::addSearchPath);
            // Enable components
            config.components(components -> components.enable(true));
            // Custom config
            builder.accept(config);
        });
    }

    /**
     * Gets the user agent string for the bot, which is used in HTTP requests to identify the bot. The user agent is constructed using the simple name of the bot's main class followed by "via LazyLibrary".
     *
     * @return  the user agent string for the bot
     */
    @NotNull
    public String getUserAgent() {
        return botClass.getSimpleName() + " via LazyLibrary";
    }

    /**
     * Checks if the given user ID is an owner of the bot.
     *
     * @param   id  the user ID to check
     *
     * @return      {@code true} if the user is an owner, {@code false} otherwise
     */
    public boolean isOwner(long id) {
        return (fileSettings.owners.primary != null && fileSettings.owners.primary == id) || fileSettings.owners.other.contains(id);
    }

    /**
     * Checks if the user who triggered the event is not an owner and replies with a no permission message if they are not.
     *
     * @param   event   the event to check
     *
     * @return          {@code true} if the user is not an owner, {@code false} otherwise
     */
    public boolean checkNotOwner(@NotNull IReplyCallback event) {
        final boolean notOwner = !isOwner(event.getUser().getIdLong());
        if (notOwner) {
            if (MessageRequest.isDefaultUseComponentsV2()) {
                event.replyComponents(LazyComponent.noPermission()).useComponentsV2().setEphemeral(true).queue();
            } else {
                event.replyEmbeds(LazyEmbed.noPermission().build()).setEphemeral(true).queue();
            }
        }
        return notOwner;
    }

    /**
     * Sets {@link #fileSettingsName}
     *
     * @param   fileSettingsName   the new value of {@link #fileSettingsName}
     *
     * @return                      {@code this}
     */
    @NotNull
    public LazyLibrary fileSettingsName(@NotNull String fileSettingsName) {
    	this.fileSettingsName = fileSettingsName;
    	return this;
    }

    /**
     * Sets {@link #loggerName}
     *
     * @param   loggerName  the new value of {@link #loggerName}
     *
     * @return              {@code this}
     */
    @NotNull
    public LazyLibrary loggerName(@NotNull String loggerName) {
    	this.loggerName = loggerName;
    	return this;
    }

    /**
     * Adds {@link GatewayIntent gateway intents} to {@link #gatewayIntents}
     *
     * @param   gatewayIntents  the {@link GatewayIntent gateway intents} to add
     *
     * @return                  {@code this}
     */
    @NotNull
    public LazyLibrary gatewayIntents(@NotNull GatewayIntent... gatewayIntents) {
        Collections.addAll(this.gatewayIntents, gatewayIntents);
    	return this;
    }

    /**
     * Sets {@link #jdaBuilder}
     *
     * @param   jdaBuilder  the new value of {@link #jdaBuilder}
     *
     * @return              {@code this}
     */
    @NotNull
    public LazyLibrary jdaBuilder(@NotNull Consumer<JDABuilder> jdaBuilder) {
    	this.jdaBuilder = jdaBuilder;
    	return this;
    }

    /**
     * Adds package paths to {@link #searchPaths}
     *
     * @param   searchPaths the package paths to add
     *
     * @return              {@code this}
     */
    @NotNull
    public LazyLibrary searchPaths(@NotNull String... searchPaths) {
        Collections.addAll(this.searchPaths, searchPaths);
    	return this;
    }

    /**
     * Sets {@link #builder}
     *
     * @param   builder the new value of {@link #builder}
     *
     * @return          {@code this}
     */
    @NotNull
    public LazyLibrary builder(@NotNull Consumer<BConfigBuilder> builder) {
    	this.builder = builder;
    	return this;
    }

    /**
     * Sets {@link #defaultComponentsV2}
     *
     * @param   defaultComponentsV2  the new value of {@link #defaultComponentsV2}
     *
     * @return                      {@code this}
     */
    @NotNull
    public LazyLibrary defaultComponentsV2(boolean defaultComponentsV2) {
    	this.defaultComponentsV2 = defaultComponentsV2;
    	return this;
    }

    /**
     * Sets {@link #defaultComponentsV2} to {@code true}
     *
     * @return  {@code this}
     */
    @NotNull
    public LazyLibrary defaultComponentsV2() {
        return defaultComponentsV2(true);
    }

    /**
     * Sets {@link #defaultStopCommand}
     *
     * @param   defaultStopCommand  the new value of {@link #defaultStopCommand}
     *
     * @return                      {@code this}
     */
    @NotNull
    public LazyLibrary defaultStopCommand(boolean defaultStopCommand) {
    	this.defaultStopCommand = defaultStopCommand;
    	return this;
    }

    /**
     * Adds default values for {@link LazyEmbed embeds} to {@link #embedDefaults}
     *
     * @param   embedDefaults   the default values for {@link LazyEmbed embeds} to add
     *
     * @return                  {@code this}
     */
    @NotNull
    public LazyLibrary embedDefaults(@NotNull Map<LazyEmbed.Key, Object> embedDefaults) {
        this.embedDefaults.putAll(embedDefaults);
    	return this;
    }

    /**
     * Adds a default value for a {@link LazyEmbed embed} to {@link #embedDefaults}
     *
     * @param   key     the {@link LazyEmbed.Key key} of the default value
     * @param   value   the default value
     *
     * @return          {@code this}
     */
    @NotNull
    public LazyLibrary embedDefault(@NotNull LazyEmbed.Key key, @NotNull Object value) {
        embedDefaults.put(key, value);
        return this;
    }

    /**
     * Adds {@link Activity activities} to {@link #activities}
     *
     * @param   activities  the {@link Activity activities} to add
     *
     * @return              {@code this}
     */
    @NotNull
    public LazyLibrary activities(@NotNull Collection<Activity> activities) {
        if (this.activities == null) this.activities = new ArrayList<>();
        Objects.requireNonNull(this.activities).addAll(activities);
        return this;
    }

    /**
     * Adds {@link Activity activities} to {@link #activities}
     *
     * @param   activities  the {@link Activity activities} to add
     *
     * @return              {@code this}
     */
    @NotNull
    public LazyLibrary activities(@NotNull Activity... activities) {
        if (this.activities == null) this.activities = new ArrayList<>();
        Collections.addAll(Objects.requireNonNull(this.activities), activities);
        return this;
    }
}
