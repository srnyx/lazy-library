package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.JDA;

import org.jetbrains.annotations.NotNull;


/**
 * Fired when a command is sent in the console
 */
public class ConsoleCommand {
    /**
     * The full raw inputted command
     */
    @NotNull private final String command;

    /**
     * Constructs a new {@link ConsoleCommand}
     *
     * @param   command the full raw inputted command
     */
    public ConsoleCommand(@NotNull String command) {
        this.command = command;
    }

    /**
     * Gets the full raw inputted command
     * <br>{@code test arg1 arg2} -> {@code test arg1 arg2}
     *
     * @return  the full raw inputted command
     */
    @NotNull
    public String getRaw() {
        return command;
    }

    /**
     * Splits the command by spaces
     * <br>{@code test arg1 arg2} -> {@code [test, arg1, arg2]}
     *
     * @return  the split elements
     */
    @NotNull
    public String[] getSplit() {
        return command.split(" ");
    }

    /**
     * Gets the first space-based element of the command (aka the command name)
     * <br>{@code test arg1 arg2} -> {@code test}
     *
     * @return  the command name
     */
    @NotNull
    public String getName() {
        return getSplit()[0];
    }

    /**
     * Gets the arguments of the command (aka everything after the command name)
     * {@code test arg1 arg2} -> {@code [arg1, arg2]}
     *
     * @return  the arguments of the command
     */
    @NotNull
    public String[] getArguments() {
        final String[] split = getSplit();
        if (split.length < 2) return new String[0];
        final String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);
        return args;
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
