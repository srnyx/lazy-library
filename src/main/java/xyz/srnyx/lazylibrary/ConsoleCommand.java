package xyz.srnyx.lazylibrary;

import org.jetbrains.annotations.NotNull;


/**
 * Fired when a command is sent in the console
 */
public class ConsoleCommand {
    /**
     * The full raw inputted command
     * <br>{@code test arg1 arg2} -> {@code test arg1 arg2}
     */
    @NotNull private final String command;
    /**
     * The split elements of the command
     * <br>{@code test arg1 arg2} -> {@code [test, arg1, arg2]}
     */
    @NotNull private final String[] split;
    /**
     * The first space-based element of the command (aka the command name)
     * <br>{@code test arg1 arg2} -> {@code test}
     */
    @NotNull private final String name;
    /**
     * The arguments of the command (aka everything after the command name)
     * <br>{@code test arg1 arg2} -> {@code [arg1, arg2]}
     */
    @NotNull private final String[] arguments;

    /**
     * Constructs a new {@link ConsoleCommand}
     *
     * @param   command the full raw inputted command
     */
    public ConsoleCommand(@NotNull String command) {
        this.command = command;
        this.split = command.split(" ");
        this.name = split[0];

        // arguments
        if (split.length < 2) {
            this.arguments = new String[0];
            return;
        }
        final String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);
        this.arguments = args;
    }

    /**
     * {@link #command}
     *
     * @return  {@link #command}
     */
    @NotNull
    public String getRaw() {
        return command;
    }

    /**
     * {@link #split}
     *
     * @return  {@link #split}
     */
    @NotNull
    public String[] getSplit() {
        return split;
    }

    /**
     * {@link #name}
     *
     * @return  {@link #name}
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * {@link #arguments}
     *
     * @return  {@link #arguments}
     */
    @NotNull
    public String[] getArguments() {
        return arguments;
    }

    @Override @NotNull
    public String toString() {
        return command;
    }

    @Override
    public int hashCode() {
        return command.hashCode();
    }
}
