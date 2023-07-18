package xyz.srnyx.lazylibrary.settings;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public record ApplicationDependency<T>(@NotNull Class<T> clazz, @NotNull Supplier<T> supplier) {}
