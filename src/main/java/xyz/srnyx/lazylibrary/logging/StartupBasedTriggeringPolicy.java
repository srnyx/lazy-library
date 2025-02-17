package xyz.srnyx.lazylibrary.logging;

import ch.qos.logback.core.rolling.TriggeringPolicyBase;

import java.io.File;


/**
 * A {@link TriggeringPolicyBase} that rolls the file on startup
 * <br>Based on <a href="https://stackoverflow.com/a/32203808/18122334">a StackOverflow answer</a>
 *
 * @param   <T> the type of the event
 */
public class StartupBasedTriggeringPolicy<T> extends TriggeringPolicyBase<T> {
    private boolean doRolling = true;

    /**
     * Constructs a new {@link StartupBasedTriggeringPolicy} with the default options
     */
    public StartupBasedTriggeringPolicy() {
        super();
    }

    @Override
    public boolean isTriggeringEvent(File activeFile, T event) {
        // Roll first time when event gets called
        if (doRolling) {
            doRolling = false;
            return true;
        }
        return false;
    }
}
