package xyz.srnyx.lazylibrary.services.power;

import com.google.gson.JsonObject;
import io.github.freya022.botcommands.api.core.service.annotations.BService;
import org.jetbrains.annotations.NotNull;
import xyz.srnyx.javautilities.http.Http;
import xyz.srnyx.javautilities.http.Response;
import xyz.srnyx.lazylibrary.LazyLibrary;

import java.util.List;


/**
 * Actions to control the power of the bot (stop, restart)
 */
@BService
public class BotPower {
    @NotNull private static final String PTERODACTYL_URL = "https://%s/api/client/servers/%s/power";

    @NotNull private final LazyLibrary library;
    @NotNull private final List<BotStopListener> listeners;

    @NotNull private final Http http;

    /**
     * Creates the service
     *
     * @param   library     the {@link LazyLibrary} instance
     * @param   listeners   the list of {@link BotStopListener}s to call when stopping
     */
    public BotPower(@NotNull LazyLibrary library, @NotNull List<BotStopListener> listeners) {
        this.library = library;
        this.listeners = listeners;

        this.http = new Http.Builder()
                .userAgent(library.getUserAgent())
                .debug(true)
                .build();
    }

    /**
     * Stops the bot immediately, without attempting to signal Pterodactyl
     */
    public void stop() {
        for (final BotStopListener listener : listeners) listener.onStop();
        System.exit(0);
    }

    /**
     * Attempts to gracefully stop the bot by sending a signal to Pterodactyl, and if that fails, stops immediately
     */
    public void gracefulStop() {
        // Attempt stop with Pterodactyl, otherwise fallback to quick stop
        if (!signalPterodactyl("stop")) stop();
    }

    /**
     * Attempts to gracefully restart the bot by sending a signal to Pterodactyl, and if that fails, stops immediately
     */
    public void gracefulRestart() {
        // Attempt restart with Pterodactyl, otherwise fallback to quick stop
        if (!signalPterodactyl("restart")) stop();
    }

    /**
     * Sends a signal to Pterodactyl to control the bot's power state (stop/restart)
     *
     * @param   signal  the signal to send ("stop" or "restart")
     *
     * @return          true if the signal was successfully sent, false otherwise
     */
    public boolean signalPterodactyl(@NotNull String signal) {
        if (library.fileSettings.pterodactyl == null) return false;

        // Send signal to Pterodactyl
        final JsonObject body = new JsonObject();
        body.addProperty("signal", signal);
        final Response response = http.postJson(
                PTERODACTYL_URL.formatted(
                        library.fileSettings.pterodactyl.panelUrl,
                        library.fileSettings.pterodactyl.serverId),
                body,
                connection -> {
                    connection.setRequestProperty("Authorization", "Bearer " + library.fileSettings.pterodactyl.apiKey);
                    connection.setRequestProperty("Accept", "Application/vnd.pterodactyl.v1+json");
                });

        // Check response
        if (response.code != null && response.code == 204) {
            LazyLibrary.LOGGER.info("Sent {} signal to Pterodactyl panel", signal);
            return true;
        }

        // If it failed, log
        LazyLibrary.LOGGER.warn("Failed to send {} signal to Pterodactyl panel!", signal);
        return false;
    }
}
