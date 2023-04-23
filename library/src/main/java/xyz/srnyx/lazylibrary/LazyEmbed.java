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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class for building {@link MessageEmbed MessageEmbeds} quickly and nicely
 */
@SuppressWarnings("UnusedReturnValue")
public class LazyEmbed {
    @NotNull private final EmbedBuilder embed = new EmbedBuilder();
    @NotNull private final Map<String, String> replacements = new HashMap<>();

    private int color;
    @Nullable private String authorName;
    @Nullable private String authorUrl;
    @Nullable private String authorIcon;
    @Nullable private String titleText;
    @Nullable private String titleUrl;
    @Nullable private String description;
    @Nullable private String thumbnail;
    @Nullable private String image;
    @NotNull private final List<MessageEmbed.Field> fields = new ArrayList<>();
    @Nullable private String footerText;
    @Nullable private String footerIcon;
    @Nullable private TemporalAccessor timestamp;

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
        for (final MessageEmbed.Field field : msgEmbed.getFields()) {
            final String name = field.getName();
            final String value = field.getValue();
            if (name != null && value != null) addField(name, value, field.isInline());
        }
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
        map.put("color", color);
        final HashMap<String, String> authorMap = new HashMap<>();
        authorMap.put("name", authorName);
        authorMap.put("url", authorUrl);
        authorMap.put("icon", authorIcon);
        map.put("author", authorMap);
        final HashMap<String, String> titleMap = new HashMap<>();
        titleMap.put("text", titleText);
        titleMap.put("url", titleUrl);
        map.put("title", titleMap);
        map.put("description", description);
        map.put("fields", fields.stream()
                .map(field -> {
                    final Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("name", field.getName());
                    fieldMap.put("value", field.getValue());
                    fieldMap.put("inline", field.isInline());
                    return fieldMap;
                })
                .toList());
        map.put("thumbnail", thumbnail);
        map.put("image", image);
        final HashMap<String, String> footerMap = new HashMap<>();
        footerMap.put("text", footerText);
        footerMap.put("icon", footerIcon);
        map.put("footer", footerMap);
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

        return embed.build();
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
        embed.setColor(color);
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
        embed.setAuthor(name, url, iconUrl);
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
        embed.setTitle(text, url);
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
        embed.setDescription(description);
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
        embed.setThumbnail(url);
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
        embed.setImage(url);
        this.image = url;
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
        final MessageEmbed.Field field = new MessageEmbed.Field(name, value, inline);
        embed.addField(field);
        fields.add(field);
        return this;
    }

    /**
     * Clears all fields from the embed
     *
     * @return  this
     */
    @NotNull
    public LazyEmbed clearFields() {
        embed.clearFields();
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
        embed.setFooter(text, iconUrl);
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
        embed.setTimestamp(timestamp);
        this.timestamp = timestamp;
        return this;
    }
}
