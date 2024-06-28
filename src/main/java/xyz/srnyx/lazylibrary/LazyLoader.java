package xyz.srnyx.lazylibrary;

import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.JDKLogAdapter;

import org.jetbrains.annotations.NotNull;

import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.logging.Logger;


public class LazyLoader {
    @NotNull public final LazyManager libraryManager = new LazyManager();

    public LazyLoader() {
        RuntimeLibrary.JDA.getLibrary(this);
        RuntimeLibrary.JAVA_UTILITIES.getLibrary(this);
        RuntimeLibrary.BOT_COMMANDS.getLibrary(this);
        RuntimeLibrary.CONFIGURATE_YAML.getLibrary(this);
        RuntimeLibrary.POSTEGRESQL.getLibrary(this);
        RuntimeLibrary.HIKARCICP.getLibrary(this);
        RuntimeLibrary.LOGBACK_CLASSIC.getLibrary(this);
    }

    public static class LazyManager extends LibraryManager {
        @NotNull private final URLClassLoaderHelper classLoader;

        public LazyManager() {
            super(new JDKLogAdapter(Logger.getLogger("LazyManager")), Path.of("."), "libs");
            classLoader = new URLClassLoaderHelper((URLClassLoader) getClass().getClassLoader(), this);
        }

        @Override
        protected void addToClasspath(@NotNull Path file) {
            classLoader.addToClasspath(file);
        }
    }
}
