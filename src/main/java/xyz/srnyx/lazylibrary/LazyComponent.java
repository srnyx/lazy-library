package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.dv8tion.jda.api.components.section.Section;
import net.dv8tion.jda.api.components.section.SectionAccessoryComponent;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.lazylibrary.emoji.LazyEmoji;


/**
 * A utility class that provides methods for creating common components used in the bot's responses.
 * It includes methods for generating error messages, permission messages, and invalid argument messages, all formatted as Container components.
 */
public class LazyComponent {
    /**
     * Creates a ContainerChildComponent that contains either a Section with an accessory or just a TextDisplay, depending on whether the accessory is null
     *
     * @param   accessory   the SectionAccessoryComponent to include in the Section, or null if no accessory is needed
     * @param   textDisplay the TextDisplay to include in the Section or return directly if no accessory is provided
     *
     * @return              a ContainerChildComponent that is either a Section with the accessory and text display or just the text display
     */
    @NotNull
    public static ContainerChildComponent getSectionElseText(@Nullable SectionAccessoryComponent accessory, @NotNull TextDisplay textDisplay) {
        return accessory == null ? textDisplay : Section.of(accessory, textDisplay);
    }

    /**
     * Creates a Container with a standardized unexpected error message, including a warning emoji and instructions for the user to try again or contact support if the issue persists.
     *
     * @return  a Container containing the unexpected error message
     */
    @NotNull
    public static Container unexpectedError() {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.WARNING_CLEAR + " Unexpected error!\n" +
                        "An unexpected error occurred, please try again!\n" +
                        "*If the issue persists, please contact support*"));
    }

    /**
     * Creates a Container with a standardized unexpected error message that includes the provided error details, along with a warning emoji and instructions for the user to try again or contact support if the issue persists.
     *
     * @param   error   the specific error details to include in the message
     *
     * @return          a Container containing the unexpected error message with the provided error details
     */
    @NotNull
    public static Container unexpectedError(@NotNull String error) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.WARNING_CLEAR + " Unexpected error!\n" +
                        "An unexpected error occurred, please try again!\n" +
                        "*If the issue persists, please contact support*\n\n" +
                        "**Error:** " + error));
    }

    /**
     * Creates a Container with a standardized no permission message, including a no entry emoji and instructions for the user that they don't have the required permissions to perform the action.
     *
     * @return  a Container containing the no permission message
     */
    @NotNull
    public static Container noPermission() {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " No permission!\n" +
                        "You don't have the required permissions to do that!"));
    }

    /**
     * Creates a Container with a standardized no permission message that includes the specific requirement needed to perform the action, along with a no entry emoji and instructions for the user that they must have the specified requirement to do that.
     *
     * @param   requirement the specific requirement (e.g., role, permission) that the user must have to perform the action
     *
     * @return              a Container containing the no permission message with the specified requirement
     */
    @NotNull
    public static Container noPermission(@NotNull Object requirement) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " No permission!\n" +
                        "You must have " + requirement + " to do that!"));
    }

    /**
     * Creates a Container with a standardized invalid argument message that includes the specific argument and its value that caused the issue, along with a no entry emoji and instructions for the user that the provided argument is invalid.
     *
     * @param   argument    the name of the argument that is invalid
     * @param   value       the value of the argument that is invalid (can be null)
     *
     * @return              a Container containing the invalid argument message with the specified argument and value
     */
    @NotNull
    public static Container invalidArgument(@NotNull String argument, @Nullable Object value) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " Invalid argument!\n" +
                        "**" + argument + ":** " + value));
    }

    /**
     * Creates a Container with a standardized invalid argument message that includes the specific argument and its value that caused the issue, along with a description of why the argument is invalid, a no entry emoji, and instructions for the user that the provided argument is invalid.
     *
     * @param   argument    the name of the argument that is invalid
     * @param   value       the value of the argument that is invalid (can be null)
     * @param   description a description explaining why the argument is invalid or what the expected format/values are
     *
     * @return              a Container containing the invalid argument message with the specified argument, value, and description
     */
    @NotNull
    public static Container invalidArgument(@NotNull String argument, @Nullable Object value, @NotNull String description) {
        return Container.of(TextDisplay.of(
                        "# " + LazyEmoji.NO_CLEAR + " Invalid argument!\n" +
                        description + "\n\n" +
                        "**" + argument + ":** " + value));
    }

    /**
     * Creates a Container with a standardized invalid arguments message that includes a list of argument names and their corresponding values that caused the issue, along with a no entry emoji and instructions for the user that the provided arguments are invalid.
     * The argumentsValues parameter should contain pairs of argument names and values (e.g., "argument1", value1, "argument2", value2, etc.).
     *
     * @param   argumentsValues             an array of objects representing pairs of argument names and their corresponding values that are invalid
     *
     * @return                              a Container containing the invalid arguments message with the specified argument names and values
     *
     * @throws  IllegalArgumentException    if the number of elements in argumentsValues is not even (i.e., each argument name does not have a corresponding value)
     */
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

    /**
     * Private constructor to prevent instantiation of this utility class, as it only contains static methods and should not be instantiated
     */
    private LazyComponent() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
