package xyz.srnyx.lazyexample;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;


public class LazyExample extends LazyLibrary {
    @Override
    public void onStart() {
        settings.loggerName = "LazyExample";
        settings.extensionsBuilder = extensionsBuilder -> extensionsBuilder.registerCommandDependency(LazyExample.class, () -> this);
    }

    public static void main(@NotNull String[] args) {
        new LazyExample();
    }
}
