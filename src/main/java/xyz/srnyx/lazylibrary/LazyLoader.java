package xyz.srnyx.lazylibrary;

import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.logging.adapters.JDKLogAdapter;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.logging.Logger;


public class LazyLoader {
    @NotNull public final JDKLogAdapter logger = new JDKLogAdapter(Logger.getLogger("LazyLoader"));
    @NotNull public final LazyManager libraryManager = new LazyManager();

    public void load() {
        RuntimeLibrary.JDA.load(this);
        RuntimeLibrary.JAVA_UTILITIES.load(this);
        RuntimeLibrary.BOT_COMMANDS.load(this);
        RuntimeLibrary.CONFIGURATE_YAML.load(this);
        RuntimeLibrary.POSTEGRESQL.load(this);
        RuntimeLibrary.HIKARCICP.load(this);
        RuntimeLibrary.LOGBACK_CLASSIC.load(this);
    }

    public class LazyManager extends LibraryManager {
        @NotNull public MockClassLoader classLoader;

        public LazyManager() {
            super(LazyLoader.this.logger, Path.of("."), "libs");
            classLoader = new MockClassLoader(new URL[0], getClass().getClassLoader());
        }

        @Override
        protected void addToClasspath(@NotNull Path file) {
            try {
                classLoader.addURL(file.toUri().toURL());
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MockClassLoader extends URLClassLoader {
        public MockClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        @Override
        public void addURL(URL url) {
            super.addURL(url);
        }
    }
}
