package xyz.srnyx.lazylibrary.settings;

import com.freya02.botcommands.api.CommandsBuilder;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.lazylibrary.LazyEmbed;
import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.*;
import java.util.function.Consumer;


/**
 * A class to hold settings for {@link LazyLibrary}
 */
@SuppressWarnings("EmptyMethod")
public class LazySettings {
    /**
     * The {@link FileSettings file settings} for the bot
     */
    @NotNull public final FileSettings fileSettings;
    /**
     * The name of the logger to use
     */
    @NotNull public String loggerName;
    /**
     * A set of {@link GatewayIntent gateway intents} to enable
     */
    @NotNull public final Set<GatewayIntent> gatewayIntents = new HashSet<>();
    /**
     * A {@link Consumer} for the {@link JDABuilder} for the bot
     */
    @Nullable public Consumer<JDABuilder> jdaBuilder = null;
    /**
     * A set of package paths to search for applications
     */
    @NotNull public final Set<String> searchPaths = new HashSet<>();
    /**
     * A set of {@link ApplicationDependency dependencies} to load
     */
    @NotNull public final Set<ApplicationDependency<?>> dependencies = new HashSet<>();
    /**
     * A {@link Consumer} for the {@link CommandsBuilder} for BotCommands
     */
    @NotNull public Consumer<CommandsBuilder> builder = empty -> {};
    /**
     * A list of default values for {@link LazyEmbed embeds}
     */
    @NotNull public Map<LazyEmbed.Key, Object> embedDefaults = new EnumMap<>(LazyEmbed.Key.class);

    /**
     * Creates a new {@link LazySettings} for the given {@link LazyLibrary}
     *
     * @param   library the {@link LazyLibrary} to create the {@link LazySettings} for
     */
    public LazySettings(@NotNull LazyLibrary library) {
    	fileSettings = new FileSettings(library);
        loggerName = library.getClass().getSimpleName();
    }

    /**
     * Sets {@link #loggerName}
     *
     * @param   loggerName  the new value of {@link #loggerName}
     *
     * @return              {@code this}
     */
    @NotNull
    public LazySettings loggerName(@NotNull String loggerName) {
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
    public LazySettings gatewayIntents(@NotNull GatewayIntent... gatewayIntents) {
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
    public LazySettings jdaBuilder(@Nullable Consumer<JDABuilder> jdaBuilder) {
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
    public LazySettings searchPaths(@NotNull String... searchPaths) {
        Collections.addAll(this.searchPaths, searchPaths);
    	return this;
    }

    /**
     * Adds {@link ApplicationDependency dependencies} to {@link #dependencies}
     *
     * @param   dependencies    the {@link ApplicationDependency dependencies} to add
     *
     * @return                  {@code this}
     */
    @NotNull
    public LazySettings dependencies(@NotNull ApplicationDependency<?>... dependencies) {
        Collections.addAll(this.dependencies, dependencies);
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
    public LazySettings builder(@NotNull Consumer<CommandsBuilder> builder) {
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
    public LazySettings embedDefaults(@NotNull Map<LazyEmbed.Key, Object> embedDefaults) {
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
    public LazySettings embedDefault(@NotNull LazyEmbed.Key key, @NotNull Object value) {
        embedDefaults.put(key, value);
        return this;
    }
}
