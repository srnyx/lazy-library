package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.annotations.BEventListener;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.MiscUtility;
import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@BService
public record ActivityRotation(@NotNull LazyLibrary library) {
    @BEventListener
    public void onReady(@NotNull ReadyEvent event) {
        final JDA jda = event.getJDA();
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            // Stop if activities is null
            if (library.activities == null) {
                scheduler.shutdown();
                return;
            }

            // Set random activity
            if (!library.activities.isEmpty()) jda.getPresence().setActivity(library.activities.get(MiscUtility.RANDOM.nextInt(library.activities.size())));
        }, 0, 3, TimeUnit.MINUTES);
    }
}
