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


@BService
public class LazyLibrary extends Stringable {
    @NotNull public static final LazyLibrary INSTANCE = new LazyLibrary();
    @NotNull public static Logger LOGGER = LoggerFactory.getLogger("LazyLibrary");

    public Class<?> botClass;
    @NotNull public String fileSettingsName = "config";
    public FileSettings fileSettings;
    @Nullable public String loggerName;
    @NotNull public final Set<String> searchPaths = new HashSet<>();
    @NotNull public Set<GatewayIntent> gatewayIntents = new HashSet<>();
    @NotNull public Set<CacheFlag> cacheFlags = new HashSet<>();
    @NotNull public Consumer<JDABuilder> jdaBuilder = _ -> {};
    @NotNull public Consumer<BConfigBuilder> builder = _ -> {};
    public boolean defaultComponentsV2 = false;
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

    @NotNull
    public String getUserAgent() {
        return botClass.getSimpleName() + " via LazyLibrary";
    }

    public boolean isOwner(long id) {
        return (fileSettings.owners.primary != null && fileSettings.owners.primary == id) || fileSettings.owners.other.contains(id);
    }

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
