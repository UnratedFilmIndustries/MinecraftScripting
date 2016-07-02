
package de.unratedfilms.scriptspace.common.script.env;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import de.unratedfilms.scriptspace.common.Consts;

/**
 * Whenever someone needs to do something with the script {@link Context}, he should submit that {@link ScriptEnvironmentTask task} to this provider
 * where it will be executed in a properly setup thread.
 * However, the user won't ever notice the fiddling with threads since he gets his return value from the same method call he submitted the task with.
 */
public class ScriptEnvironmentProvider {

    // Represents null without actually being null; required for BlockingQueue, since they don't accept null elements
    private static final Object                            NULL        = new Object();

    private static final Thread                            SCRIPT_ENVIRONMENT_THREAD;

    // Note that all blocking queue implementations are guaranteed to be thread-safe
    private static BlockingQueue<ScriptEnvironmentTask<?>> taskQueue   = new LinkedBlockingQueue<>();
    private static BlockingQueue<Object>                   resultQueue = new LinkedBlockingQueue<>();

    static {

        // Start up the thread which provides the environment
        SCRIPT_ENVIRONMENT_THREAD = new Thread(new ScriptEnvironmentThreadRunnable(), Consts.MOD_NAME + " script environment thread");
        SCRIPT_ENVIRONMENT_THREAD.start();

    }

    @SuppressWarnings ("unchecked")
    public static <R> R invokeInScriptEnvironment(ScriptEnvironmentTask<R> task) {

        // Put the task in the task queue, so that it will be executed as soon as possible
        taskQueue.offer(task);

        try {
            // As soon as the result is available, return it
            Object result = resultQueue.take();
            return result == NULL ? null : (R) result;
        } catch (InterruptedException e) {
            LOGGER.info("Interrupted while waiting for result of script environment task; exiting stack by throwing a runtime exception now");
            throw new RuntimeException("Exit stack", e);
        }
    }

    private ScriptEnvironmentProvider() {

    }

    public static interface ScriptEnvironmentTask<R> {

        public R invoke(Context context, Scriptable globalScope);

    }

    private static class ScriptEnvironmentThreadRunnable implements Runnable {

        private Context    context;
        private Scriptable globalScope;

        @Override
        public void run() {

            initializeContext();

            while (true) {
                try {
                    // Wait for the next task to become available
                    ScriptEnvironmentTask<?> nextTask = taskQueue.take();

                    // As soon as a tasks becomes available invoke that task
                    Object result = nextTask.invoke(context, globalScope);

                    // Put the result in the result queue, so that the original invocation method can return it to the user
                    resultQueue.offer(result == null ? NULL : result);
                } catch (InterruptedException e) {
                    LOGGER.info("Script environment thread was interrupted; exiting now, no further tasks will be executed");
                    break;
                }
            }
        }

        private void initializeContext() {

            context = new SandboxContextFactory().enterContext();
            globalScope = new ImporterTopLevel(context); /* calls context.initStandardObjects(); */

            ContextConfigurator.configureContext(context, globalScope);
        }

    }

}
