package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.service.annotations.BService;
import io.github.freya022.botcommands.api.localization.interaction.GuildLocaleProvider;
import io.github.freya022.botcommands.api.localization.interaction.UserLocaleProvider;
import io.github.freya022.botcommands.api.localization.text.TextCommandLocaleProvider;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.Interaction;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;


/**
 * A service that provides locale information for interactions, guilds, and text commands. This implementation currently returns English as the default locale for all cases.
 */
@BService
public class LocaleProvider implements UserLocaleProvider, GuildLocaleProvider, TextCommandLocaleProvider {
    /**
     * Constructs a new LocaleProvider instance. This constructor is empty as there are no initialization steps required for this implementation.
     */
    public LocaleProvider() {}

    @Override @NotNull
    public Locale getLocale(@NotNull Interaction interaction) {
        return Locale.ENGLISH;
    }

    @Override @NotNull
    public Locale getLocale(@NotNull MessageReceivedEvent messageReceivedEvent) {
        return DiscordLocale.ENGLISH_US.toLocale();
    }
}
