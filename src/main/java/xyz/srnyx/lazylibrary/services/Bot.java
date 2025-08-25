package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.service.annotations.InterfacedService;

import net.dv8tion.jda.api.JDA;

import org.jetbrains.annotations.NotNull;


@InterfacedService(acceptMultiple = false)
public interface Bot {
    @NotNull
    JDA getJDA();
}
