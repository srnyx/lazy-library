package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.List;


/**
 * A class for building {@link MessageEmbed MessageEmbeds} quickly and nicely
 */
@SuppressWarnings("UnusedReturnValue")
public class LazyEmbed {
    /**
     * Default values if a value is {@code null} in an {@link LazyEmbed embed}
     */
    @NotNull public static final Map<Key, Object> DEFAULTS = new EnumMap<>(Key.class);

    /**
     * The {@link EmbedBuilder embed builder} that is used to build the {@link MessageEmbed}
     */
    @NotNull public final EmbedBuilder builder = new EmbedBuilder();
    /**
     * Replacements for all values that will be replaced when building the {@link MessageEmbed}
     */
    @NotNull protected final Map<String, String> replacements = new HashMap<>();

    /**
     * The color of the embed
     */
    protected int color;
    /**
     * The author of the embed
     */
    @Nullable protected String authorName;
    /**
     * The URL of the author
     */
    @Nullable protected String authorUrl;
    /**
     * The icon of the author
     */
    @Nullable protected String authorIcon;
    /**
     * The title of the embed
     */
    @Nullable protected String titleText;
    /**
     * The URL of the title
     */
    @Nullable protected String titleUrl;
    /**
     * The description of the embed
     */
    @Nullable protected String description;
    /**
     * The thumbnail of the embed (right)
     */
    @Nullable protected String thumbnail;
    /**
     * The image of the embed (below)
     */
    @Nullable protected String image;
    /**
     * The fields of the embed
     */
    @NotNull protected final List<MessageEmbed.Field> fields = new ArrayList<>();
    /**
     * The footer of the embed
     */
    @Nullable protected String footerText;
    /**
     * The icon of the footer
     */
    @Nullable protected String footerIcon;
    /**
     * The timestamp of the embed
     */
    @Nullable protected TemporalAccessor timestamp;

    /**
     * Constructs a new {@link LazyEmbed}
     */
    public LazyEmbed() {}

    /**
     * Constructs a new {@link LazyEmbed} from a {@link ConfigurationNode}
     *
     * @param   node    the {@link ConfigurationNode} to construct from
     */
    public LazyEmbed(@NotNull ConfigurationNode node) {
        if (node.empty()) return;

        // Get some values/nodes
        final int colorValue = node.node("color").getInt();
        final ConfigurationNode authorNode = node.node("author");
        final ConfigurationNode titleNode = node.node("title");
        final ConfigurationNode fieldsNode = node.node("fields");
        final ConfigurationNode footerNode = node.node("footer");
        final long timestampValue = node.node("timestamp").getLong();

        // Set the values
        if (colorValue != 0) setColor(colorValue);
        setAuthor(authorNode.node("name").getString(), authorNode.node("url").getString(), authorNode.node("icon").getString());
        setTitle(titleNode.node("text").getString(), titleNode.node("url").getString());
        setDescription(node.node("description").getString());
        if (!fieldsNode.empty()) for (final ConfigurationNode field : fieldsNode.childrenList()) {
            final String name = field.node("name").getString();
            final String value = field.node("value").getString();
            if (name != null && value != null) addField(name, value, field.node("inline").getBoolean());
        }
        setThumbnail(node.node("thumbnail").getString());
        setImage(node.node("image").getString());
        setFooter(footerNode.node("text").getString(), footerNode.node("icon").getString());
        if (timestampValue != 0) setTimestamp(Instant.ofEpochMilli(timestampValue));
    }

    /**
     * Constructs a new {@link LazyEmbed} from a {@link MessageEmbed}
     *
     * @param   msgEmbed    the {@link MessageEmbed} to copy
     */
    public LazyEmbed(@NotNull MessageEmbed msgEmbed) {
        // Get some values
        final Color colorValue = msgEmbed.getColor();
        final MessageEmbed.AuthorInfo newAuthor = msgEmbed.getAuthor();
        final MessageEmbed.Thumbnail newThumbnail = msgEmbed.getThumbnail();
        final MessageEmbed.ImageInfo newImage = msgEmbed.getImage();
        final MessageEmbed.Footer newFooter = msgEmbed.getFooter();

        if (colorValue != null) setColor(colorValue);
        if (newAuthor != null) setAuthor(newAuthor.getName(), newAuthor.getUrl(), newAuthor.getIconUrl());
        setTitle(msgEmbed.getTitle(), msgEmbed.getUrl());
        setDescription(msgEmbed.getDescription());
        addFields(msgEmbed.getFields());
        if (newThumbnail != null) setThumbnail(newThumbnail.getUrl());
        if (newImage != null) setImage(newImage.getUrl());
        if (newFooter != null) setFooter(newFooter.getText(), newFooter.getIconUrl());
        setTimestamp(msgEmbed.getTimestamp());
    }

    /**
     * Converts this {@link LazyEmbed} to a {@link Map}
     *
     * @return  the {@link Map} representation of this {@link LazyEmbed}
     */
    @NotNull
    public Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<>();
        if (color != 0) map.put("color", color);
        if (authorName != null) {
            final Map<String, String> authorMap = new HashMap<>();
            authorMap.put("name", authorName);
            if (authorUrl != null) authorMap.put("url", authorUrl);
            if (authorIcon != null) authorMap.put("icon", authorIcon);
            map.put("author", authorMap);
        }
        if (titleText != null) {
            final Map<String, String> titleMap = new HashMap<>();
            titleMap.put("text", titleText);
            if (titleUrl != null) titleMap.put("url", titleUrl);
            map.put("title", titleMap);
        }
        if (description != null) map.put("description", description);
        if (!fields.isEmpty()) map.put("fields", fields.stream()
                .map(field -> {
                    final String name = field.getName();
                    if (name == null) return null;
                    final String value = field.getValue();
                    final Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("name", name);
                    if (value != null) fieldMap.put("value", value);
                    fieldMap.put("inline", field.isInline());
                    return fieldMap;
                })
                .filter(Objects::nonNull)
                .toList());
        if (thumbnail != null) map.put("thumbnail", thumbnail);
        if (image != null) map.put("image", image);
        if (footerText != null) {
            final Map<String, String> footerMap = new HashMap<>();
            footerMap.put("text", footerText);
            if (footerIcon != null) footerMap.put("icon", footerIcon);
            map.put("footer", footerMap);
        }
        if (timestamp != null) map.put("timestamp", timestamp.getLong(ChronoField.INSTANT_SECONDS) * 1000 + timestamp.getLong(ChronoField.MILLI_OF_SECOND));
        return map;
    }

    /**
     * Replaces a key with a value in all parameters of the {@link LazyEmbed embed}
     *
     * @param   key     the key to replace
     * @param   value   the value to replace the key with
     *
     * @return          the {@link LazyEmbed} instance
     */
    @NotNull
    public LazyEmbed replace(@NotNull String key, @Nullable Object value) {
        replacements.put(key, String.valueOf(value));
        return this;
    }

    /**
     * Builds the {@link MessageEmbed}
     *
     * @return  the {@link MessageEmbed}
     */
    @NotNull
    public MessageEmbed build() {
        // Get replaceable values
        String authorNameReplace = authorName;
        String titleTextReplace = titleText;
        String descriptionReplace = description;
        String footerTextReplace = footerText;

        // Parse replacements for the values
        for (final Map.Entry<String, String> entry : replacements.entrySet()) {
            if (authorNameReplace != null) authorNameReplace = authorNameReplace.replace(entry.getKey(), entry.getValue());
            if (titleTextReplace != null) titleTextReplace = titleTextReplace.replace(entry.getKey(), entry.getValue());
            if (descriptionReplace != null) descriptionReplace = descriptionReplace.replace(entry.getKey(), entry.getValue());
            if (footerTextReplace != null) footerTextReplace = footerTextReplace.replace(entry.getKey(), entry.getValue());
        }

        // Set the values
        setAuthor(authorNameReplace, authorUrl, authorIcon);
        setTitle(titleTextReplace, titleUrl);
        setDescription(descriptionReplace);
        setFooter(footerTextReplace, footerIcon);

        // Fields
        final List<MessageEmbed.Field> fieldsCopy = new ArrayList<>(fields);
        clearFields();
        for (final MessageEmbed.Field field : fieldsCopy) {
            // Get name and value
            String name = field.getName();
            String value = field.getValue();

            // Parse replacements for the name and value
            for (final Map.Entry<String, String> entry : replacements.entrySet()) {
                if (name != null) name = name.replace(entry.getKey(), entry.getValue());
                if (value != null) value = value.replace(entry.getKey(), entry.getValue());
            }

            // Add the new field
            if (name != null && value != null) addField(name, value, field.isInline());
        }
        
        // Set defaults
        for (final Key key : Key.values()) {
            final Object value = DEFAULTS.get(key);
            if (value != null) switch (key) {
                case COLOR -> {
                    if (color == 0) color = (int) value;
                }
                case AUTHOR_NAME -> {
                    if (authorName != null) authorName = (String) value;
                } 
                case AUTHOR_URL -> {
                    if (authorUrl != null) authorUrl = (String) value;
                }
                case AUTHOR_ICON -> {
                    if (authorIcon != null) authorIcon = (String) value;
                }
                case TITLE_TEXT -> {
                    if (titleText != null) titleText = (String) value;
                }
                case TITLE_URL -> {
                    if (titleUrl != null) titleUrl = (String) value;
                }
                case DESCRIPTION -> {
                    if (description != null) description = (String) value;
                }
                case THUMBNAIL -> {
                    if (thumbnail != null) thumbnail = (String) value;
                }
                case IMAGE -> {
                    if (image != null) image = (String) value;
                }
                case FOOTER_TEXT -> {
                    if (footerText != null) footerText = (String) value;
                }
                case FOOTER_ICON -> {
                    if (footerIcon != null) footerIcon = (String) value;
                }
                case TIMESTAMP -> {
                    if (timestamp != null) timestamp = (TemporalAccessor) value;
                }
                default -> throw new IllegalStateException("Unexpected value: " + key);
            }
        }

        return builder.build();
    }

    /**
     * Sets the color of the embed
     *
     * @param   color   the {@link Color} of the embed
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setColor(@NotNull Color color) {
        return setColor(color.getRGB());
    }

    /**
     * Sets the color of the embed
     *
     * @param   color   the color of the embed (RGB integer)
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setColor(int color) {
        builder.setColor(color);
        this.color = color;
        return this;
    }

    /**
     * Sets the author of the embed
     *
     * @param   name    the name of the author
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setAuthor(@Nullable String name) {
        return setAuthor(name, null, null);
    }

    /**
     * Sets the author of the embed
     *
     * @param   name    the name of the author
     * @param   url     the url of the author
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setAuthor(@Nullable String name, @Nullable String url) {
        return setAuthor(name, url, null);
    }

    /**
     * Sets the author of the embed
     *
     * @param   name    the name of the author
     * @param   url     the url of the author
     * @param   iconUrl the icon url of the author
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setAuthor(@Nullable String name, @Nullable String url, @Nullable String iconUrl) {
        builder.setAuthor(name, url, iconUrl);
        authorName = name;
        authorUrl = url;
        authorIcon = iconUrl;
        return this;
    }

    /**
     * Sets the title of the embed
     *
     * @param   text    the text of the title
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setTitle(@Nullable String text) {
        return setTitle(text, null);
    }

    /**
     * Sets the title of the embed
     *
     * @param   text    the text of the title
     * @param   url     the url of the title
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setTitle(@Nullable String text, @Nullable String url) {
        builder.setTitle(text, url);
        this.titleText = text;
        this.titleUrl = url;
        return this;
    }

    /**
     * Sets the description of the embed
     *
     * @param   description the description of the embed
     *
     * @return              this
     */
    @NotNull
    public LazyEmbed setDescription(@Nullable String description) {
        builder.setDescription(description);
        this.description = description;
        return this;
    }

    /**
     * Sets the thumbnail of the embed
     *
     * @param   url the url of the thumbnail
     *
     * @return      this
     */
    @NotNull
    public LazyEmbed setThumbnail(@Nullable String url) {
        builder.setThumbnail(url);
        this.thumbnail = url;
        return this;
    }

    /**
     * Sets the image of the embed
     *
     * @param   url the url of the image
     *
     * @return      this
     */
    @NotNull
    public LazyEmbed setImage(@Nullable String url) {
        builder.setImage(url);
        this.image = url;
        return this;
    }

    /**
     * Adds a field to the embed
     *
     * @param   field   the field to add
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed addField(@NotNull MessageEmbed.Field field) {
        builder.addField(field);
        fields.add(field);
        return this;
    }

    /**
     * Adds a field to the embed
     *
     * @param   name    the name/title of the field
     * @param   value   the value of the field
     * @param   inline  whether the field should be inline or not
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed addField(@NotNull String name, @NotNull String value, boolean inline) {
        return addField(new MessageEmbed.Field(name, value, inline));
    }

    /**
     * Adds multiple fields to the embed
     *
     * @param   newFields   the fields to add
     *
     * @return              this
     */
    @NotNull
    public LazyEmbed addFields(@NotNull Collection<MessageEmbed.Field> newFields) {
        newFields.forEach(builder::addField);
        fields.addAll(newFields);
        return this;
    }

    /**
     * Clears all fields from the embed
     *
     * @return  this
     */
    @NotNull
    public LazyEmbed clearFields() {
        builder.clearFields();
        fields.clear();
        return this;
    }

    /**
     * Sets the footer of the embed
     *
     * @param   text    the text of the footer
     * @param   iconUrl the icon url of the footer
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setFooter(@Nullable String text, @Nullable String iconUrl) {
        builder.setFooter(text, iconUrl);
        this.footerText = text;
        this.footerIcon = iconUrl;
        return this;
    }

    /**
     * Sets the timestamp of the embed
     *
     * @param   timestamp   the timestamp of the embed
     *
     * @return              this
     */
    @NotNull
    public LazyEmbed setTimestamp(@Nullable TemporalAccessor timestamp) {
        builder.setTimestamp(timestamp);
        this.timestamp = timestamp;
        return this;
    }

    /**
     * All possible (defaultable) keys an {@link LazyEmbed embed} can have ({@link LazyEmbed#DEFAULTS})
     */
    public enum Key {
        /**
         * {@link LazyEmbed#color}
         */
        COLOR,
        /**
         * {@link LazyEmbed#authorName}
         */
        AUTHOR_NAME,
        /**
         * {@link LazyEmbed#authorUrl}
         */
        AUTHOR_URL,
        /**
         * {@link LazyEmbed#authorIcon}
         */
        AUTHOR_ICON,
        /**
         * {@link LazyEmbed#titleText}
         */
        TITLE_TEXT,
        /**
         * {@link LazyEmbed#titleUrl}
         */
        TITLE_URL,
        /**
         * {@link LazyEmbed#description}
         */
        DESCRIPTION,
        /**
         * {@link LazyEmbed#fields}
         */
        THUMBNAIL,
        /**
         * {@link LazyEmbed#image}
         */
        IMAGE,
        /**
         * {@link LazyEmbed#footerText}
         */
        FOOTER_TEXT,
        /**
         * {@link LazyEmbed#footerIcon}
         */
        FOOTER_ICON,
        /**
         * {@link LazyEmbed#timestamp}
         */
        TIMESTAMP
    }
}
