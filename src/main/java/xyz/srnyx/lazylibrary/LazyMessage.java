package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class that allows you to create/save a message with {@link String content} and {@link LazyEmbed embeds}
 */
public class LazyMessage {
    /**
     * The {@link String content} of the message
     */
    @Nullable public String content;
    /**
     * The {@link LazyEmbed embeds} of the message
     */
    @NotNull public final List<LazyEmbed> embeds = new ArrayList<>();

    /**
     * Constructs a new {@link LazyMessage} from the given {@link Message}
     *
     * @param   message the {@link Message} to construct the {@link LazyMessage} from
     */
    public LazyMessage(@NotNull Message message) {
        this(message.getContentRaw(), message.getEmbeds().stream()
                .map(LazyEmbed::new)
                .toList());
    }

    /**
     * Constructs a new {@link LazyMessage} with the given {@link String content} and {@link LazyEmbed embeds}
     *
     * @param   content the {@link String content} of the message
     * @param   embeds  the {@link LazyEmbed embeds} of the message
     */
    public LazyMessage(@Nullable String content, @Nullable List<LazyEmbed> embeds) {
        if (embeds == null) embeds = new ArrayList<>();
        this.content = content;
        this.embeds.addAll(embeds);
    }

    /**
     * Converts the {@link LazyMessage} to a {@link Map}
     *
     * @return  the {@link Map} representation of the {@link LazyMessage}
     */
    @NotNull
    public Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        if (!embeds.isEmpty()) map.put("embeds", embeds.stream()
                .map(LazyEmbed::toMap)
                .toList());
        return map;
    }

    /**
     * Converts the {@link LazyMessage} to a {@link MessageCreateBuilder}
     *
     * @param   library the {@link LazyLibrary} to construct the {@link LazyEmbed embeds} with
     *
     * @return          the {@link MessageCreateBuilder} representation of the {@link LazyMessage}
     */
    @NotNull
    public MessageCreateBuilder toBuilder(@NotNull LazyLibrary library) {
        final MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.setContent(content);
        builder.setEmbeds(embeds.stream()
                .map(embed -> embed.build(library))
                .toList());
        return builder;
    }

    /**
     * Sets the {@link String content} of the {@link LazyMessage}
     *
     * @param   content the {@link String content} of the {@link LazyMessage}
     *
     * @return          this
     */
    @NotNull
    public LazyMessage setContent(@Nullable String content) {
        this.content = content;
        return this;
    }

    /**
     * Adds the given {@link LazyEmbed embed} to the {@link LazyMessage}
     *
     * @param   embed   the {@link LazyEmbed embed} to add to the {@link LazyMessage}
     *
     * @return          this
     */
    @NotNull
    public LazyMessage addEmbed(@NotNull LazyEmbed embed) {
        embeds.add(embed);
        return this;
    }

    /**
     * Adds the given {@link LazyEmbed embeds} to the {@link LazyMessage}
     *
     * @param   embeds  the {@link LazyEmbed embeds} to add to the {@link LazyMessage}
     *
     * @return          this
     */
    @NotNull
    public LazyMessage addEmbeds(@NotNull List<LazyEmbed> embeds) {
        this.embeds.addAll(embeds);
        return this;
    }

    /**
     * Gets the built {@link MessageEmbed embeds} ({@link MessageEmbed}) of the {@link LazyMessage}
     *
     * @param   library the {@link LazyLibrary} to construct the {@link LazyEmbed embeds} with
     *
     * @return          the built {@link MessageEmbed embeds} ({@link MessageEmbed}) of the {@link LazyMessage}
     */
    @NotNull
    public List<MessageEmbed> getBuiltEmbeds(@NotNull LazyLibrary library) {
        return embeds.stream()
                .map(embed -> embed.build(library))
                .toList();
    }
}
