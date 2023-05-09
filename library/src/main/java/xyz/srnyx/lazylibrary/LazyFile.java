package xyz.srnyx.lazylibrary;

import org.jetbrains.annotations.NotNull;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


/**
 * Class for file management (loading, creating, saving, etc...)
 */
public class LazyFile {
    /**
     * The path to the file
     */
    @NotNull private final String pathString;
    /**
     * Whether the file is a resource to be loaded from the JAR file
     */
    private final boolean isResource;
    /**
     * The {@link File} of the file
     */
    @NotNull public final File file;
    /**
     * The {@link YamlConfigurationLoader loader} for the file
     */
    @NotNull public final YamlConfigurationLoader loader;
    /**
     * The {@link ConfigurationNode YML data} of the file
     */
    public ConfigurationNode yaml;

    /**
     * Creates a new {@link LazyFile}
     *
     * @param   pathString  the path to the file
     * @param   style       the {@link NodeStyle YML style} of the file
     * @param   isResource  whether the file is a resource to be loaded from the JAR file
     */
    public LazyFile(@NotNull String pathString, @NotNull NodeStyle style, boolean isResource) {
        this.pathString = pathString + ".yml";
        this.isResource = isResource;
        this.file = new File(this.pathString);
        this.loader = YamlConfigurationLoader.builder().nodeStyle(style).path(file.toPath()).build();
        load();
    }

    /**
     * Loads the file
     */
    public void load() {
        if (isResource && !file.exists()) create();
        try {
            this.yaml = loader.load();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the file
     */
    public void create() {
        if (!isResource) {
            try {
                Files.createDirectories(file.toPath().getParent());
                Files.createFile(file.toPath());
            } catch (final IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (final InputStream inputStream = LazyLibrary.class.getClassLoader().getResourceAsStream(pathString);
             final FileOutputStream outputStream = new FileOutputStream(pathString)) {
            if (inputStream == null) throw new IOException("Resource not found in JAR file.");
            final byte [] buffer = new byte[inputStream.available()];
            int count;
            while ((count = inputStream.read(buffer)) > 0) outputStream.write(buffer, 0, count);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the file
     */
    public void save() {
        // Delete file and cancel if it isn't a resource, is empty, and exists
        if (!isResource && yaml.empty() && file.exists()) {
            try {
                Files.delete(file.toPath());
            } catch (final IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Save file
        try {
            loader.save(yaml);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a {@link LazyEmbed} from the file
     *
     * @param   path    the path to the embed
     *
     * @return          the {@link LazyEmbed}
     */
    @NotNull
    public LazyEmbed getEmbed(@NotNull Object... path) {
        return new LazyEmbed(yaml.node(path));
    }
}
