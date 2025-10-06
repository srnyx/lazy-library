package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;


public class LazyComponent {
    @NotNull
    public static Container unexpectedError() {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.WARNING_CLEAR + " Unexpected error!\n" +
                        "An unexpected error occurred, please try again!\n" +
                        "*If the issue persists, please contact support*"));
    }

    @NotNull
    public static Container unexpectedError(@NotNull String error) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.WARNING_CLEAR + " Unexpected error!\n" +
                        "An unexpected error occurred, please try again!\n" +
                        "*If the issue persists, please contact support*\n\n" +
                        "**Error:** " + error));
    }

    @NotNull
    public static Container noPermission() {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " No permission!\n" +
                        "You don't have the required permissions to do that!"));
    }

    @NotNull
    public static Container noPermission(@NotNull Object requirement) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " No permission!\n" +
                        "You must have " + requirement + " to do that!"));
    }

    @NotNull
    public static Container invalidArgument(@NotNull String argument, @Nullable Object value) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " Invalid argument!\n" +
                        "**" + argument + ":** " + value));
    }

    @NotNull
    public static Container invalidArgument(@NotNull String argument, @Nullable Object value, @NotNull String description) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " Invalid argument!\n" +
                        description + "\n\n" +
                        "**" + argument + ":** " + value));
    }

    @NotNull
    public static Container invalidArguments(@NotNull Object... argumentsValues) {
        if (argumentsValues.length % 2 != 0) throw new IllegalArgumentException("Each argument must have a value!");
        final StringBuilder description = new StringBuilder();
        for (int i = 0; i < argumentsValues.length; i += 2) {
            description.append("**").append(argumentsValues[i]).append(":** ").append(argumentsValues[i + 1]).append("\n");
        }
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " Invalid arguments!\n" +
                        description));
    }
}
