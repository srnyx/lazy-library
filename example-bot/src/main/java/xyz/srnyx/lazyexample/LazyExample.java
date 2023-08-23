package xyz.srnyx.lazyexample;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;
import xyz.srnyx.lazylibrary.settings.LazySettings;

import java.util.function.Consumer;


/**
 * An example of a bot using {@link LazyLibrary}
 */
public class LazyExample extends LazyLibrary {
    /**
     * Starts the bot
     */
    private LazyExample() {}

    @Override @NotNull
    public Consumer<LazySettings> getSettings() {
        return newSettings -> newSettings.searchPaths("xyz.srnyx.lazyexample.commands");
    }

    /**
     * Required main method to start the bot
     *
     * @param   args    the arguments provided when running the java command
     */
    public static void main(@NotNull String[] args) {
        new LazyExample();
    }
}
