package xyz.srnyx.lazylibrary.services.stopper;

import com.google.gson.JsonObject;

import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.HttpUtility;

import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.List;
import java.util.Optional;


@BService
public class BotStopper {
    @NotNull private static final String PTERODACTYL_URL = "https://%s/api/client/servers/%s/power";

    @NotNull private final LazyLibrary library;
    @NotNull private final List<BotStopListener> listeners;

    public BotStopper(@NotNull LazyLibrary library, @NotNull List<BotStopListener> listeners) {
        this.library = library;
        this.listeners = listeners;
    }

    public void stop() {
        for (final BotStopListener listener : listeners) listener.onStop();
        System.exit(0);
    }

    public void gracefulStop() {
        // Attempt stop with Pterodactyl
        if (library.fileSettings.pterodactyl != null) {
            // Send stop signal to Pterodactyl
            final JsonObject body = new JsonObject();
            body.addProperty("signal", "stop");
            final Optional<HttpUtility.Response> response = HttpUtility.postJson(
                    library.getUserAgent(),
                    PTERODACTYL_URL.formatted(
                            library.fileSettings.pterodactyl.panelUrl,
                            library.fileSettings.pterodactyl.serverId),
                    body,
                    connection -> {
                        connection.setRequestProperty("Authorization", "Bearer " + library.fileSettings.pterodactyl.apiKey);
                        connection.setRequestProperty("Accept", "Application/vnd.pterodactyl.v1+json");
                    });

            // Check response
            if (response.isPresent() && response.get().code == 204) {
                 LazyLibrary.LOGGER.info("Sent stop signal to Pterodactyl panel, shutting down...");
                return;
            }

            // If it failed, log and fallback to quick stop
            LazyLibrary.LOGGER.warn("Failed to send stop signal to Pterodactyl panel! Falling back to quick stop...");
        }

        // Fallback to quick stop
        stop();
    }
}
