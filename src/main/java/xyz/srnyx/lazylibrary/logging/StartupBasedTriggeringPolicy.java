package xyz.srnyx.lazylibrary.logging;

import ch.qos.logback.core.rolling.TriggeringPolicyBase;

import java.io.File;


// https://stackoverflow.com/a/32203808/18122334
public class StartupBasedTriggeringPolicy<T> extends TriggeringPolicyBase<T> {
    private boolean doRolling = true;

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
