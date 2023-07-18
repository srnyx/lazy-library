package xyz.srnyx.lazyexample;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;
import xyz.srnyx.lazylibrary.settings.ApplicationDependency;
import xyz.srnyx.lazylibrary.settings.LazySettings;

import java.util.function.Consumer;


public class LazyExample extends LazyLibrary {
    @Override @NotNull
    public Consumer<LazySettings> getSettings() {
        return settings -> settings.dependencies.add(new ApplicationDependency<>(LazyExample.class, () -> this));
    }

    public static void main(@NotNull String[] args) {
        new LazyExample();
    }
}
