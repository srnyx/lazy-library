package xyz.srnyx.lazylibrary;

import io.github.freya022.botcommands.api.commands.application.provider.GlobalApplicationCommandManager;
import io.github.freya022.botcommands.api.commands.application.provider.GuildApplicationCommandManager;
import io.github.freya022.botcommands.api.core.BotCommands;
import io.github.freya022.botcommands.api.core.config.BConfigBuilder;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import kotlin.jvm.JvmClassMappingKt;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.srnyx.javautilities.parents.Stringable;
import xyz.srnyx.lazylibrary.services.Bot;

import java.util.*;
import java.util.function.Consumer;


@BService
public class LazyLibrary extends Stringable {
    @NotNull public static final LazyLibrary INSTANCE = new LazyLibrary();
    @NotNull public static Logger LOGGER = LoggerFactory.getLogger("LazyLibrary");

    public Class<? extends Bot> botClass;
    @NotNull public String fileSettingsName = "config";
    public FileSettings fileSettings;
    @Nullable public String loggerName;
    @NotNull public final Set<String> searchPaths = new HashSet<>();
    @NotNull public Set<GatewayIntent> gatewayIntents = new HashSet<>();
    @NotNull public Set<CacheFlag> cacheFlags = new HashSet<>();
    @Nullable public Consumer<JDABuilder> jdaBuilder;
    @NotNull public Consumer<BConfigBuilder> builder = _ -> {};
    public boolean defaultStopCommand = true;
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

    public void build(@NotNull Class<? extends Bot> botClass) {
        this.botClass = botClass;

        // Set logger
        if (loggerName != null) {
            LOGGER = LoggerFactory.getLogger(loggerName);
        } else {
            LOGGER = LoggerFactory.getLogger(botClass);
        }

        // Get FileSettings
        fileSettings = new FileSettings(fileSettingsName);

        // Set default contexts
        GlobalApplicationCommandManager.Defaults.INSTANCE.setContexts(InteractionContextType.ALL);
        GuildApplicationCommandManager.Defaults.INSTANCE.setContexts(Collections.singleton(InteractionContextType.GUILD));

        // Create BotCommands
        BotCommands.create(config -> {
            // LazySettings service
            config.services(services -> services.registerServiceSupplier(JvmClassMappingKt.getKotlinClass(LazyLibrary.class), _ -> LazyLibrary.INSTANCE));
            // Disable help text command
            config.textCommands(textCommands -> textCommands.disableHelp(true));
            // Owners
            if (fileSettings.ownersPrimary != null) config.addPredefinedOwners(fileSettings.ownersPrimary);
            fileSettings.ownersOther.forEach(config::addPredefinedOwners);
            // Search paths
            searchPaths.forEach(config::addSearchPath);
            // Enable components
            config.components(components -> components.enable(true));
            // Custom config
            builder.accept(config);
        });
    }

    public boolean isOwner(long id) {
        return (fileSettings.ownersPrimary != null && fileSettings.ownersPrimary == id) || fileSettings.ownersOther.contains(id);
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
    public LazyLibrary jdaBuilder(@Nullable Consumer<JDABuilder> jdaBuilder) {
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
