package xyz.srnyx.lazylibrary.settings;

import com.freya02.botcommands.api.CommandsBuilder;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;

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
     * A {@link Consumer} for the {@link CommandsBuilder} for BotCommands
     */
    @NotNull public Consumer<CommandsBuilder> builder = empty -> {};
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

    public LazySettings(@NotNull String fileName) {
    	fileSettings = new FileSettings(fileName);
    }
}
