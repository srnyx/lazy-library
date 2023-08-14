package xyz.srnyx.lazylibrary.settings;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


/**
 * A record to store data for an application dependency
 *
 * @param   clazz       the {@link Class} of the dependency
 * @param   supplier    the {@link Supplier} of the dependency
 *
 * @param   <T>         the type of the dependency
 */
public record ApplicationDependency<T>(@NotNull Class<T> clazz, @NotNull Supplier<T> supplier) {}
