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


@BService
public class LocaleProvider implements UserLocaleProvider, GuildLocaleProvider, TextCommandLocaleProvider {
    @Override @NotNull
    public Locale getLocale(@NotNull Interaction interaction) {
        return Locale.ENGLISH;
    }

    @Override @NotNull
    public DiscordLocale getDiscordLocale(@NotNull Interaction interaction) {
        return DiscordLocale.ENGLISH_US;
    }

    @Override @NotNull
    public DiscordLocale getDiscordLocale(@NotNull MessageReceivedEvent event) {
        return DiscordLocale.ENGLISH_US;
    }
}
