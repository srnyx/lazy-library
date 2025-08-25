package xyz.srnyx.lazylibrary.services.consolecommand;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.javautilities.objects.Arguments;


/**
 * Fired when a command is sent in the console
 */
public class ConsoleCommand extends Arguments {
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
     * Constructs a new {@link ConsoleCommand}
     *
     * @param   command the full raw inputted command
     */
    public ConsoleCommand(@NotNull String command) {
        super(getArguments(command));
        this.command = command;
        this.split = command.split(" ");
        this.name = split[0];
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

    @Override @NotNull
    public String toString() {
        return command;
    }

    @Override
    public int hashCode() {
        return command.hashCode();
    }

    @NotNull
    private static String[] getArguments(@NotNull String command) {
        final String[] split = command.split(" ");
        if (split.length < 2) return new String[0];
        final String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);
        return args;
    }
}
