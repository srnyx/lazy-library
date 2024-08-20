package xyz.srnyx.lazylibrary;

import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.function.Function;


/**
 * All runtime libraries that Lazy Library downloads
 */
public enum RuntimeLibrary {
    /**
     * net.dv8tion:JDA
     */
    JDA("https://repo1.maven.org/maven2/", loader -> Library.builder()
            .groupId("net{}dv8tion")
            .artifactId("JDA")
            .version("5.0.0-beta.24").build()),
    /**
     * xyz.srnyx:java-utilities
     */
    JAVA_UTILITIES("https://jitpack.io/", loader -> Library.builder()
            .groupId("xyz{}srnyx")
            .artifactId("java-utilities")
            .version("a71551bc2d").build()),
    /**
     * org.javassist:javassist
     */
    BOT_COMMANDS("https://repo1.maven.org/maven2/", loader -> Library.builder()
            .groupId("io{}github{}freya022")
            .artifactId("BotCommands")
            .version("2.10.3").build()),
    /**
     * org.spongepowered:configurate-yaml
     */
    CONFIGURATE_YAML("https://repo1.maven.org/maven2/", loader -> Library.builder()
            .groupId("org{}spongepowered")
            .artifactId("configurate-yaml")
            .version("4.1.2").build()),
    /**
     * org.postgresql:postgresql
     */
    POSTEGRESQL("https://repo1.maven.org/maven2/", loader -> Library.builder()
            .groupId("org{}postgresql")
            .artifactId("postgresql")
            .version("42.7.3").build()),
    /**
     * com.zaxxer:HikariCP
     */
    HIKARCICP("https://repo1.maven.org/maven2/", loader -> Library.builder()
            .groupId("com{}zaxxer")
            .artifactId("HikariCP")
            .version("5.1.0").build()),
    /**
     * ch.qos.logback:logback-classic
     */
    LOGBACK_CLASSIC("https://repo1.maven.org/maven2/", loader -> Library.builder()
            .groupId("ch{}qos{}logback")
            .artifactId("logback-classic")
            .version("1.5.3").build());

    /**
     * The repositories to add before loading the library
     */
    @NotNull public final TreeSet<String> repositories;
    /**
     * The library to load
     */
    @NotNull public final Function<LazyLoader, Library> library;

    /**
     * Creates a new {@link RuntimeLibrary}
     *
     * @param   repositories    {@link #repositories}
     * @param   library         {@link #library}
     */
    RuntimeLibrary(@NotNull Collection<String> repositories, @NotNull Function<LazyLoader, Library> library) {
        this.repositories = new TreeSet<>(repositories);
        this.library = library;
    }

    /**
     * Creates a new {@link RuntimeLibrary} with a single repository
     *
     * @param   repository  the repository to add
     * @param   library     the library to load
     */
    RuntimeLibrary(@NotNull String repository, @NotNull Function<LazyLoader, Library> library) {
        this.repositories = new TreeSet<>(Collections.singleton(repository));
        this.library = library;
    }

    /**
     * Gets the library to load
     *
     * @param   loader  the {@link LazyLoader} to load the library into
     */
    @NotNull
    public Library getLibrary(@NotNull LazyLoader loader) {
        return library.apply(loader);
    }

    /**
     * Downloads and loads the library into the specified bot
     *
     * @param   loader  the {@link LazyLoader} to load the library into
     */
    public void load(@NotNull LazyLoader loader) {
        final LibraryManager manager = loader.libraryManager;
        for (final String repository : repositories) manager.addRepository(repository);
        manager.loadLibrary(getLibrary(loader));
    }
}
