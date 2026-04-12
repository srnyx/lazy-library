package xyz.srnyx.lazylibrary.services;

import io.github.freya022.botcommands.api.core.annotations.BEventListener;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.MiscUtility;

import xyz.srnyx.lazylibrary.LazyLibrary;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Manages automatic activity/status rotation
 */
@BService
public final class ActivityRotation {
    /**
     * The time between activity rotations (default: 3 minutes)
     */
    @NotNull public static Duration ROTATION_TIME = Duration.ofMinutes(3);

    @NotNull private final LazyLibrary library;

    /**
     * Creates the service
     *
     * @param   library     the {@link LazyLibrary} instance
     */
    public ActivityRotation(@NotNull LazyLibrary library) {
        this.library = library;
    }

    /**
     * Starts the activity rotation task, which will set a random activity from the list every 3 minutes
     *
     * @param   event   the {@link ReadyEvent} that triggers the start of the rotation
     */
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
        }, 0, ROTATION_TIME.getSeconds(), TimeUnit.SECONDS);
    }
}
