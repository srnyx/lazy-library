package xyz.srnyx.lazyexample.commands;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Dependency;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazyexample.LazyExample;
import xyz.srnyx.lazylibrary.LazyEmbed;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@CommandMarker
public class ServersCmd extends ApplicationCommand {
    @Dependency private LazyExample bot;

    @JDASlashCommand(
            scope = CommandScope.GLOBAL,
            name = "servers",
            description = "Lists all servers the bot is in")
    public void serversCommand(@NotNull GlobalSlashEvent event) {
        if (!bot.isOwner(event.getUser().getIdLong())) {
            event.replyEmbeds(new LazyEmbed()
                            .setColor(Color.RED)
                            .setTitle("No permission!")
                            .setDescription("You don't have permission to use this command")
                            .setFooter("/servers")
                            .build(bot))
                    .setEphemeral(true).queue();
            return;
        }
        final JDA jda = event.getJDA();

        // Get guilds
        final Map<Guild, Integer> guildsMap = jda.getGuilds().stream()
                .map(guild -> Map.entry(guild, guild.getMemberCount()))
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));

        // Send embed
        final LazyEmbed embed = new LazyEmbed()
                .setTitle(jda.getSelfUser().getName() + " servers")
                .setDescription("**Total servers:** " + guildsMap.size() + "\n**Total members:** " + guildsMap.values().stream()
                        .mapToInt(Integer::intValue)
                        .sum());
        guildsMap.forEach((guild, members) -> embed.addField(guild.getName(), "**ID:** `" + guild.getId() + "`\n**Members:** " + members, true));
        event.replyEmbeds(embed.build(bot)).setEphemeral(true).queue();
    }
}
