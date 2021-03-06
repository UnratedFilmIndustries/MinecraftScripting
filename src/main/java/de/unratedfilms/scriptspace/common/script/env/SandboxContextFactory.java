
package de.unratedfilms.scriptspace.common.script.env;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

/**
 * The core of our sandbox.
 * This creates our special context, sets the wrap factory, and observes the time each script takes to execute, eventually killing it if necessary.
 *
 * @see ContextFactory
 */
class SandboxContextFactory extends ContextFactory {

    private static final int STACK_DEPTH           = 16383;
    private static final int INSTRUCTION_THRESHOLD = 10000;
    private static final int MAX_TIME              = 30 * 1000; // 30 seconds

    /**
     * @return A context that calls observeInstructionCount every 10,000 bytecode instructions.
     */
    @Override
    protected Context makeContext() {

        TimedContext cx = new TimedContext(this);
        cx.setLanguageVersion(Context.VERSION_1_7);
        cx.setOptimizationLevel(-1);
        cx.setMaximumInterpreterStackDepth(STACK_DEPTH);
        cx.setInstructionObserverThreshold(INSTRUCTION_THRESHOLD);
        cx.setClassShutter(new SandboxClassShutter());
        cx.setWrapFactory(new SandboxWrapFactory());
        return cx;
    }

    @Override
    protected void observeInstructionCount(Context cx, int instructionCount) throws Error {

        TimedContext mcx = (TimedContext) cx;
        long currentTime = System.currentTimeMillis();
        if (currentTime - mcx.startTime > MAX_TIME) {
            throw new Error("Script exceeded maximum runtime length");
        }
    }

    // TODO Possibly lookup which script is being executed and set the max time based off of its type
    @Override
    protected Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {

        TimedContext mcx = (TimedContext) cx;
        mcx.startTime = System.currentTimeMillis();
        return super.doTopCall(callable, cx, scope, thisObj, args);
    }

    private static class TimedContext extends Context {

        private long startTime;

        public TimedContext(ContextFactory factory) {

            super(factory);
        }

    }

}
