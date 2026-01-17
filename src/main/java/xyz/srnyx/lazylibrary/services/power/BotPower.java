package xyz.srnyx.lazylibrary.services.power;

import com.google.gson.JsonObject;

import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.HttpUtility;

import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.List;
import java.util.Optional;


@BService
public class BotPower {
    @NotNull private static final String PTERODACTYL_URL = "https://%s/api/client/servers/%s/power";

    @NotNull private final LazyLibrary library;
    @NotNull private final List<BotStopListener> listeners;

    public BotPower(@NotNull LazyLibrary library, @NotNull List<BotStopListener> listeners) {
        this.library = library;
        this.listeners = listeners;
    }

    public void stop() {
        for (final BotStopListener listener : listeners) listener.onStop();
        System.exit(0);
    }

    public void gracefulStop() {
        // Attempt stop with Pterodactyl, otherwise fallback to quick stop
        if (!signalPterodactyl("stop")) stop();
    }

    public void gracefulRestart() {
        // Attempt restart with Pterodactyl, otherwise fallback to quick stop
        if (!signalPterodactyl("restart")) stop();
    }

    public boolean signalPterodactyl(@NotNull String signal) {
        if (library.fileSettings.pterodactyl == null) return false;

        // Send signal to Pterodactyl
        final JsonObject body = new JsonObject();
        body.addProperty("signal", signal);
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
             LazyLibrary.LOGGER.info("Sent {} signal to Pterodactyl panel", signal);
            return true;
        }

        // If it failed, log
        LazyLibrary.LOGGER.warn("Failed to send {} signal to Pterodactyl panel!", signal);
        return false;
    }
}
