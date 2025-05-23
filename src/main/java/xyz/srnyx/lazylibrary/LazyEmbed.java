package xyz.srnyx.lazylibrary;

import com.google.gson.*;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;

import xyz.srnyx.javautilities.parents.Stringable;

import xyz.srnyx.lazylibrary.settings.LazySettings;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


/**
 * A class for building {@link MessageEmbed MessageEmbeds} quickly and nicely
 */
@SuppressWarnings("UnusedReturnValue")
public class LazyEmbed extends Stringable {
    /**
     * The {@link EmbedBuilder embed builder} that is used to build the {@link MessageEmbed}
     */
    @NotNull public final EmbedBuilder builder = new EmbedBuilder();
    /**
     * Replacements for all values that will be replaced when building the {@link MessageEmbed}
     */
    @NotNull public final Map<String, String> replacements = new HashMap<>();
    /**
     * The {@link Key keys} that are disabled from being set by the {@link LazySettings#embedDefaults}
     */
    @NotNull public final Set<Key> disabledDefaults = new HashSet<>();

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
     * Constructs a new {@link LazyEmbed} from another {@link LazyEmbed}
     *
     * @param   lazyEmbed   the {@link LazyEmbed} to copy
     */
    public LazyEmbed(@NotNull LazyEmbed lazyEmbed) {
        replacements.putAll(lazyEmbed.replacements);
        disabledDefaults.addAll(lazyEmbed.disabledDefaults);

        // Embed data
        setColor(lazyEmbed.color);
        setAuthor(lazyEmbed.authorName, lazyEmbed.authorUrl, lazyEmbed.authorIcon);
        setTitle(lazyEmbed.titleText, lazyEmbed.titleUrl);
        setDescription(lazyEmbed.description);
        setThumbnail(lazyEmbed.thumbnail);
        setImage(lazyEmbed.image);
        addFields(lazyEmbed.fields);
        setFooter(lazyEmbed.footerText, lazyEmbed.footerIcon);
        setTimestamp(lazyEmbed.timestamp);
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
     * Constructs a new {@link LazyEmbed} from a JSON string
     * <p><i>Only if the JSON is from {@link MessageEmbed#toData()}</i>
     *
     * @param   json    the JSON string to construct from
     */
    public LazyEmbed(@NotNull String json) {
        final JsonObject object;
        try {
            object = JsonParser.parseString(json).getAsJsonObject();
        } catch (final JsonParseException | IllegalStateException e) {
            return;
        }

        // Color
        final JsonPrimitive newColor = object.getAsJsonPrimitive("color");
        if (newColor != null) setColor(newColor.getAsInt());

        // Author
        final JsonObject author = object.getAsJsonObject("author");
        if (author != null) {
            final JsonPrimitive name = author.getAsJsonPrimitive("name");
            if (name != null) {
                final JsonPrimitive url = author.getAsJsonPrimitive("url");
                final JsonPrimitive iconUrl = author.getAsJsonPrimitive("icon_url");
                setAuthor(name.getAsString(), url == null ? null : url.getAsString(), iconUrl == null ? null : iconUrl.getAsString());
            }
        }

        // Title
        final JsonPrimitive title = object.getAsJsonPrimitive("title");
        if (title != null) {
            final JsonPrimitive url = object.getAsJsonPrimitive("url");
            setTitle(title.getAsString(), url == null ? null : url.getAsString());
        }

        // Description
        final JsonPrimitive newDescription = object.getAsJsonPrimitive("description");
        setDescription(newDescription == null ? null : newDescription.getAsString());

        // Fields
        final JsonArray newFields = object.getAsJsonArray("fields");
        if (newFields != null) for (final JsonElement field : newFields) {
            if (!field.isJsonObject()) continue;
            final JsonObject fieldObject = field.getAsJsonObject();
            final JsonPrimitive name = fieldObject.getAsJsonPrimitive("name");
            final JsonPrimitive value = fieldObject.getAsJsonPrimitive("value");
            if (name == null || value == null) continue;
            final JsonPrimitive inline = fieldObject.getAsJsonPrimitive("inline");
            addField(name.getAsString(), value.getAsString(), inline != null && inline.getAsBoolean());
        }

        // Thumbnail
        final JsonObject newThumbnail = object.getAsJsonObject("thumbnail");
        if (newThumbnail != null) {
            final JsonPrimitive thumbnailUrl = newThumbnail.getAsJsonPrimitive("url");
            setThumbnail(thumbnailUrl == null ? null : thumbnailUrl.getAsString());
        }

        // Image
        final JsonObject newImage = object.getAsJsonObject("image");
        if (newImage != null) {
            final JsonPrimitive imageUrl = newImage.getAsJsonPrimitive("url");
            setImage(imageUrl == null ? null : imageUrl.getAsString());
        }

        // Footer
        final JsonObject footer = object.getAsJsonObject("footer");
        if (footer != null) {
            final JsonPrimitive newFooterText = footer.getAsJsonPrimitive("text");
            final JsonPrimitive footerIconUrl = footer.getAsJsonPrimitive("icon_url");
            setFooter(newFooterText == null ? null : newFooterText.getAsString(), footerIconUrl == null ? null : footerIconUrl.getAsString());
        }

        // Timestamp
        final JsonPrimitive newTimestamp = object.getAsJsonPrimitive("timestamp");
        if (newTimestamp != null) setTimestamp(Instant.ofEpochMilli(newTimestamp.getAsLong()));
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
     * Copies this {@link LazyEmbed} as a new instance
     *
     * @return  the new {@link LazyEmbed} instance
     */
    @NotNull
    public LazyEmbed copy() {
        return new LazyEmbed(this);
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
     * Replaces multiple keys with values in all parameters of the {@link LazyEmbed embed}
     *
     * @param   replacements    the replacements to make
     *
     * @return                  the {@link LazyEmbed} instance
     */
    @NotNull
    public LazyEmbed replace(@NotNull Map<String, Object> replacements) {
        for (final Map.Entry<String, Object> entry : replacements.entrySet()) replace(entry.getKey(), entry.getValue());
        return this;
    }

    /**
     * Disables keys from being set by the {@link LazySettings#embedDefaults}
     *
     * @param   keys    the keys to disable
     *
     * @return          the {@link LazyEmbed} instance
     */
    @NotNull
    public LazyEmbed disableDefaults(@NotNull Key... keys) {
        return disableDefaults(Arrays.asList(keys));
    }

    /**
     * Disables keys from being set by the {@link LazySettings#embedDefaults}
     *
     * @param   keys    the keys to disable
     *
     * @return          the {@link LazyEmbed} instance
     */
    @NotNull
    public LazyEmbed disableDefaults(@NotNull Collection<Key> keys) {
        disabledDefaults.addAll(keys);
        return this;
    }

    /**
     * Enables keys to be set by the {@link LazySettings#embedDefaults}
     * <br>All keys are enabled by default
     *
     * @param   keys    the keys to enable
     *
     * @return          the {@link LazyEmbed} instance
     *
     * @see             #disableDefaults(Key...)
     */
    @NotNull
    public LazyEmbed enableDefaults(@NotNull Key... keys) {
        return enableDefaults(Arrays.asList(keys));
    }

    /**
     * Enables keys to be set by the {@link LazySettings#embedDefaults}
     * <br>All keys are enabled by default
     *
     * @param   keys    the keys to enable
     *
     * @return          the {@link LazyEmbed} instance
     *
     * @see             #disableDefaults(Collection)
     */
    @NotNull
    public LazyEmbed enableDefaults(@NotNull Collection<Key> keys) {
        disabledDefaults.removeAll(keys);
        return this;
    }

    /**
     * Convenience method for {@link Factory#Factory(LazyEmbed) new Factory(LazyEmbed)} using this {@link LazyEmbed}
     *
     * @return  the {@link Factory} instance
     */
    @NotNull
    public Factory toFactory() {
        return new Factory(this);
    }

    /**
     * Builds the {@link MessageEmbed}
     *
     * @param   library the {@link LazyLibrary} instance
     *
     * @return          the {@link MessageEmbed}
     */
    @NotNull
    public MessageEmbed build(@NotNull LazyLibrary library) {
        // Replacements
        if (!replacements.isEmpty()) {
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

            // Set the new values
            setAuthor(authorNameReplace, authorUrl, authorIcon);
            setTitle(titleTextReplace, titleUrl);
            setDescription(descriptionReplace);
            setFooter(footerTextReplace, footerIcon);

            // Fields
            final List<MessageEmbed.Field> newFields = new ArrayList<>(fields);
            clearFields();
            for (final MessageEmbed.Field field : newFields) {
                // Get name and value
                String name = field.getName();
                String value = field.getValue();
                if (name == null || value == null) continue;

                // Parse replacements for the name and value
                for (final Map.Entry<String, String> entry : replacements.entrySet()) {
                    name = name.replace(entry.getKey(), entry.getValue());
                    value = value.replace(entry.getKey(), entry.getValue());
                }

                // Add the new field
                addField(name, value, field.isInline());
            }
        }
        
        // Set defaults
        for (final Map.Entry<Key, Object> entry : library.settings.embedDefaults.entrySet()) {
            final Key key = entry.getKey();
            if (disabledDefaults.contains(key)) continue;
            final Object value = entry.getValue();
            if (value != null) key.setter.accept(this, value);
        }

        return builder.build();
    }

    /**
     * Get {@link #color}
     *
     * @return  {@link #color}
     */
    public int getColor() {
        return color;
    }

    /**
     * Get {@link #authorName}
     *
     * @return  {@link #authorName}
     */
    @Nullable
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Get {@link #authorUrl}
     *
     * @return  {@link #authorUrl}
     */
    @Nullable
    public String getAuthorUrl() {
        return authorUrl;
    }

    /**
     * Get {@link #authorIcon}
     *
     * @return  {@link #authorIcon}
     */
    @Nullable
    public String getAuthorIcon() {
        return authorIcon;
    }

    /**
     * Get {@link #titleText}
     *
     * @return  {@link #titleText}
     */
    @Nullable
    public String getTitleText() {
        return titleText;
    }

    /**
     * Get {@link #titleUrl}
     *
     * @return  {@link #titleUrl}
     */
    @Nullable
    public String getTitleUrl() {
        return titleUrl;
    }

    /**
     * Get {@link #description}
     *
     * @return  {@link #description}
     */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Get {@link #thumbnail}
     *
     * @return  {@link #thumbnail}
     */
    @Nullable
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Get {@link #image}
     *
     * @return  {@link #image}
     */
    @Nullable
    public String getImage() {
        return image;
    }

    /**
     * Returns an unmodifiable list of all fields in the embed
     *
     * @return  an unmodifiable list of all fields in the embed
     */
    @NotNull
    public List<MessageEmbed.Field> getFields() {
        return Collections.unmodifiableList(fields);
    }

    /**
     * Returns a field from the embed by its name
     *
     * @param   name    the name of the field
     *
     * @return          the field, or {@link Optional#empty() empty} if not found
     */
    @NotNull
    public Optional<MessageEmbed.Field> getField(@NotNull String name) {
        for (final MessageEmbed.Field field : fields) {
            final String fieldName = field.getName();
            if (fieldName != null && fieldName.equals(name)) return Optional.of(field);
        }
        return Optional.empty();
    }

    /**
     * Returns the value of a field from the embed by its name
     *
     * @param   name    the name of the field
     *
     * @return          the value of the field, or {@link Optional#empty() empty} if not found
     */
    @NotNull
    public Optional<String> getFieldValue(@NotNull String name) {
        return getField(name).map(MessageEmbed.Field::getValue);
    }

    /**
     * Get {@link #footerText}
     *
     * @return  {@link #footerText}
     */
    @Nullable
    public String getFooterText() {
        return footerText;
    }

    /**
     * Get {@link #footerIcon}
     *
     * @return  {@link #footerIcon}
     */
    @Nullable
    public String getFooterIcon() {
        return footerIcon;
    }

    /**
     * Get {@link #timestamp}
     *
     * @return  {@link #timestamp}
     */
    @Nullable
    public TemporalAccessor getTimestamp() {
        return timestamp;
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
        if (description != null && description.isEmpty()) description = null;
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
     * Adds multiple fields to the embed
     *
     * @param   newFields   the fields to add
     *
     * @return              this
     */
    @NotNull
    public LazyEmbed addFields(@NotNull MessageEmbed.Field... newFields) {
        return addFields(Arrays.asList(newFields));
    }

    /**
     * Adds an empty field to the embed
     *
     * @param   inline  whether the field should be inline or not
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed addEmptyField(boolean inline) {
        return addField("", "", inline);
    }

    /**
     * Adds multiple empty fields to the embed
     *
     * @param   amount  the amount of empty fields to add
     * @param   inline  whether the fields should be inline or not
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed addEmptyFields(int amount, boolean inline) {
        for (int i = 0; i < amount; i++) addEmptyField(inline);
        return this;
    }

    /**
     * Creates a grid of fields in the embed
     * <br><b>WARNING: These look pretty bad on mobile!</b>
     *
     * @param   rows    the rows of fields to add
     *
     * @return          this
     *
     * @see             #gridFields(Map...)
     */
    @NotNull
    public LazyEmbed gridFields(@NotNull Collection<Map<String, String>> rows) {
        for (final Map<String, String> row : rows) {
            // Add fields
            int added = 0;
            for (final Map.Entry<String, String> entry : row.entrySet()) {
                final String value = entry.getValue();
                if (value == null || value.isEmpty()) continue;
                addField(entry.getKey(), entry.getValue(), true);
                added++;
            }
            // Add empty fields to make the grid
            final int remainder = added % 3;
            if (remainder != 0) addEmptyFields(3 - remainder, true);
        }
        return this;
    }

    /**
     * Creates a grid of fields in the embed
     * <br><b>WARNING: These look pretty bad on mobile!</b>
     *
     * @param   rows    the rows of fields to add
     *
     * @return          this
     *
     * @see             #gridFields(Collection)
     */
    @NotNull @SafeVarargs
    public final LazyEmbed gridFields(@NotNull Map<String, String>... rows) {
        return gridFields(Arrays.asList(rows));
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
     *
     * @return          this
     */
    @NotNull
    public LazyEmbed setFooter(@Nullable String text) {
        return setFooter(text, null);
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
     * Sets the timestamp of the embed
     *
     * @param   timestamp   the timestamp of the embed
     *
     * @return              this
     */
    @NotNull
    public LazyEmbed setTimestamp(@Nullable Long timestamp) {
        return setTimestamp(timestamp == null ? null : Instant.ofEpochMilli(timestamp));
    }

    /**
     * Sets the timestamp of the embed
     *
     * @param   timestamp   the timestamp of the embed
     *
     * @return              this
     */
    @NotNull
    public LazyEmbed setTimestamp(@Nullable Date timestamp) {
        return setTimestamp(timestamp == null ? null : timestamp.getTime());
    }

    /**
     * A factory for storing a base {@link LazyEmbed} to copy from
     */
    public static class Factory {
        /**
         * The base {@link LazyEmbed} to copy from
         */
        @NotNull private LazyEmbed embed;

        /**
         * Constructs a new {@link Factory} with an empty {@link LazyEmbed}
         */
        public Factory() {
            this.embed = new LazyEmbed();
        }

        /**
         * Constructs a new {@link Factory} with a base {@link LazyEmbed}
         *
         * @param   embed   the base {@link LazyEmbed}
         */
        public Factory(@NotNull LazyEmbed embed) {
            this.embed = embed;
        }

        /**
         * Returns a new {@link LazyEmbed} from the base {@link LazyEmbed}
         *
         * @return  the new {@link LazyEmbed}
         */
        @NotNull
        public LazyEmbed newEmbed() {
            return embed.copy();
        }

        /**
         * Sets the base {@link LazyEmbed}
         *
         * @param   embed   the base {@link LazyEmbed}
         *
         * @return          this
         */
        @NotNull
        public Factory setEmbed(@NotNull LazyEmbed embed) {
            this.embed = embed;
            return this;
        }

        /**
         * Updates the base {@link LazyEmbed} with a consumer
         *
         * @param   updater the consumer to update the base {@link LazyEmbed}
         *
         * @return          this
         */
        @NotNull
        public Factory updateEmbed(@NotNull Consumer<LazyEmbed> updater) {
            updater.accept(embed);
            return this;
        }
    }

    /**
     * All possible (defaultable) keys an {@link LazyEmbed embed} can have ({@link LazySettings#embedDefaults})
     */
    public enum Key {
        /**
         * {@link LazyEmbed#color}
         */
        COLOR((embed, value) -> {
            if (embed.color == 0) embed.setColor((int) value);
        }),
        /**
         * {@link LazyEmbed#authorName}
         */
        AUTHOR_NAME((embed, value) -> {
            if (embed.authorName == null) embed.setAuthor((String) value, embed.authorUrl, embed.authorIcon);
        }),
        /**
         * {@link LazyEmbed#authorUrl}
         */
        AUTHOR_URL((embed, value) -> {
            if (embed.authorUrl == null) embed.setAuthor(embed.authorName, (String) value, embed.authorIcon);
        }),
        /**
         * {@link LazyEmbed#authorIcon}
         */
        AUTHOR_ICON((embed, value) -> {
            if (embed.authorIcon == null) embed.setAuthor(embed.authorName, embed.authorUrl, (String) value);
        }),
        /**
         * {@link LazyEmbed#titleText}
         */
        TITLE_TEXT((embed, value) -> {
            if (embed.titleText == null) embed.setTitle((String) value, embed.titleUrl);
        }),
        /**
         * {@link LazyEmbed#titleUrl}
         */
        TITLE_URL((embed, value) -> {
            if (embed.titleUrl == null) embed.setTitle(embed.titleText, (String) value);
        }),
        /**
         * {@link LazyEmbed#description}
         */
        DESCRIPTION((embed, value) -> {
            if (embed.description == null) embed.setDescription((String) value);
        }),
        /**
         * {@link LazyEmbed#fields}
         */
        THUMBNAIL((embed, value) -> {
            if (embed.thumbnail == null) embed.setThumbnail((String) value);
        }),
        /**
         * {@link LazyEmbed#image}
         */
        IMAGE((embed, value) -> {
            if (embed.image == null) embed.setImage((String) value);
        }),
        /**
         * {@link LazyEmbed#footerText}
         */
        FOOTER_TEXT((embed, value) -> {
            if (embed.footerText == null) embed.setFooter((String) value, embed.footerIcon);
        }),
        /**
         * {@link LazyEmbed#footerIcon}
         */
        FOOTER_ICON((embed, value) -> {
            if (embed.footerIcon == null) embed.setFooter(embed.footerText, (String) value);
        }),
        /**
         * {@link LazyEmbed#timestamp}
         */
        TIMESTAMP((embed, value) -> {
            if (embed.timestamp == null) embed.setTimestamp((TemporalAccessor) value);
        });

        /**
         * The setter for the key
         */
        @NotNull public final BiConsumer<LazyEmbed, Object> setter;

        /**
         * Creates a new key
         *
         * @param   setter  {@link #setter}
         */
        Key(@NotNull BiConsumer<LazyEmbed, Object> setter) {
            this.setter = setter;
        }
    }

    /**
     * A pre-built {@link LazyEmbed} for when an unexpected error occurs
     *
     * @return  the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed unexpectedError() {
        return new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.WARNING_CLEAR + " Unexpected error!")
                .setDescription("An unexpected error occurred, please try again!\n*If the issue persists, please contact support*");
    }

    /**
     * A pre-built {@link LazyEmbed} for when an unexpected error occurs
     *
     * @param   error   a short description of the error that occurred
     *
     * @return          the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed unexpectedError(@NotNull String error) {
        return new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.WARNING_CLEAR + " Unexpected error!")
                .setDescription("An unexpected error occurred, please try again!\n*If the issue persists, please contact support*")
                .addField("Error", error, true);
    }

    /**
     * A pre-built {@link LazyEmbed} for when a user doesn't have permission to do something
     *
     * @return  the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed noPermission() {
        return new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.NO_CLEAR + " No permission!")
                .setDescription("You don't have the required permissions to do that!");
    }

    /**
     * A pre-built {@link LazyEmbed} for when a user doesn't have permission to do something
     *
     * @param   requirement the requirement the user doesn't have (ex: role mention)
     *
     * @return              the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed noPermission(@NotNull Object requirement) {
        return new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.NO_CLEAR + " No permission!")
                .setDescription("You must have " + requirement + " to do that!");
    }

    /**
     * A pre-built {@link LazyEmbed} for when an invalid argument is provided
     *
     * @param   argument    the argument that is invalid
     * @param   value       the value that was provided
     *
     * @return              the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed invalidArgument(@NotNull String argument, @Nullable Object value) {
        return new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.NO_CLEAR + " Invalid argument!")
                .addField(argument, String.valueOf(value), true);
    }

    /**
     * A pre-built {@link LazyEmbed} for when an invalid argument is provided
     *
     * @param   argument    the argument that is invalid
     * @param   value       the value that was provided
     * @param   description the description of the embed
     *
     * @return              the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed invalidArgument(@NotNull String argument, @Nullable Object value, @NotNull String description) {
        return new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.NO_CLEAR + " Invalid argument!")
                .setDescription(description)
                .addField(argument, String.valueOf(value), true);
    }

    /**
     * A pre-built {@link LazyEmbed} for when multiple provided arguments are invalid
     *
     * @param   argumentsValues             the arguments and values that are invalid (argument1, value1, argument2, value2, ...)
     *
     * @return                              the {@link LazyEmbed}
     *
     * @throws  IllegalArgumentException    if the amount of arguments is not even (each argument must have a value)
     */
    @NotNull
    public static LazyEmbed invalidArguments(@NotNull Object... argumentsValues) {
        if (argumentsValues.length % 2 != 0) throw new IllegalArgumentException("Each argument must have a value!");
        final LazyEmbed embed = new LazyEmbed()
                .setColor(Color.RED)
                .setTitle(LazyEmoji.NO_CLEAR + " Invalid arguments!");
        for (int i = 0; i < argumentsValues.length; i += 2) embed.addField(String.valueOf(argumentsValues[i]), String.valueOf(argumentsValues[i + 1]), true);
        return embed;
    }

    /**
     * A pre-built {@link LazyEmbed} that has no color ({@code #2b2d31}, which blends in with the embed background) and has all default key values disabled
     * <br>It's basically just a completely empty embed/rectangle for you to fill in
     * <br>Works great with {@link LazyEmbed#gridFields(Collection)}
     *
     * @return  the {@link LazyEmbed}
     */
    @NotNull
    public static LazyEmbed empty() {
        return new LazyEmbed()
                .setColor(0x242429)
                .disableDefaults(Key.values());
    }
}
