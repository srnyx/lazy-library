package xyz.srnyx.lazylibrary.settings;

import com.freya02.botcommands.api.CommandsBuilder;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;


/**
 * A class to hold settings for {@link LazyLibrary}
 */
@SuppressWarnings("EmptyMethod")
public class LazySettings {
    @NotNull public final FileSettings fileSettings;
    /**
     * The name of the logger to use
     */
    @NotNull public String loggerName = getClass().getSimpleName();
    /**
     * A set of {@link GatewayIntent gateway intents} to enable
     */
    @NotNull public final Set<GatewayIntent> gatewayIntents = new HashSet<>();
    /**
     * A set of {@link CacheFlag cache flags} to disable
     */
    @NotNull public final Set<CacheFlag> disabledCacheFlags = new HashSet<>();
    /**
     * A set of package paths to search for applications
     */
    @NotNull public final Set<String> searchPaths = new HashSet<>();
    @NotNull public final Set<ApplicationDependency<?>> dependencies = new HashSet<>();
    /**
     * A {@link Consumer} for the {@link CommandsBuilder} for BotCommands
     */
    @NotNull public Consumer<CommandsBuilder> builder = empty -> {};

    public LazySettings(@NotNull String fileName) {
    	fileSettings = new FileSettings(fileName);
    }

    @NotNull
    public LazySettings loggerName(@NotNull String loggerName) {
    	this.loggerName = loggerName;
    	return this;
    }

    @NotNull
    public LazySettings gatewayIntents(@NotNull GatewayIntent... gatewayIntents) {
        Collections.addAll(this.gatewayIntents, gatewayIntents);
    	return this;
    }

    @NotNull
    public LazySettings disabledCacheFlags(@NotNull CacheFlag... disabledCacheFlags) {
        Collections.addAll(this.disabledCacheFlags, disabledCacheFlags);
    	return this;
    }

    @NotNull
    public LazySettings searchPaths(@NotNull String... searchPaths) {
        Collections.addAll(this.searchPaths, searchPaths);
    	return this;
    }

    @NotNull
    public LazySettings dependencies(@NotNull ApplicationDependency<?>... dependencies) {
        Collections.addAll(this.dependencies, dependencies);
    	return this;
    }

    @NotNull
    public LazySettings builder(@NotNull Consumer<CommandsBuilder> builder) {
    	this.builder = builder;
    	return this;
    }
}
