
package de.unratedfilms.scriptspace.common.script.env;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
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
    private static final Object                NULL = new Object();

    private static ThreadLocal<Implementation> impl;

    static {

        impl = new ThreadLocal<Implementation>() {

            @Override
            protected Implementation initialValue() {

                return new Implementation();
            }

        };

    }

    public static <R> R invokeInScriptEnvironment(ScriptEnvironmentTask<R> task) {

        return impl.get().invokeInScriptEnvironment(task);
    }

    public static interface ScriptEnvironmentTask<R> {

        public R invoke(Context context, Scriptable globalScope);

    }

    private static class Implementation {

        private Thread                                        scriptEnvThread;

        private CountDownLatch                                threadInitializationLatch;
        private boolean                                       threadSuccessfullyInitialized;

        // Note that all blocking queue implementations are guaranteed to be thread-safe
        private final BlockingQueue<ScriptEnvironmentTask<?>> taskQueue   = new LinkedBlockingQueue<>();
        private final BlockingQueue<Object>                   resultQueue = new LinkedBlockingQueue<>();

        @SuppressWarnings ("unchecked")
        private <R> R invokeInScriptEnvironment(ScriptEnvironmentTask<R> task) {

            lazilyInitialize();

            // Put the task in the task queue, so that it will be executed as soon as possible
            taskQueue.offer(task);

            try {
                // As soon as the result is available, return it
                Object result = resultQueue.take();

                if (result == NULL) {
                    return null;
                } else if (result instanceof InvocationThrowableWrapper) {
                    Throwable throwable = ((InvocationThrowableWrapper) result).throwable;
                    if (throwable instanceof RuntimeException) {
                        throw (RuntimeException) throwable;
                    } else {
                        throw (Error) throwable;
                    }
                } else {
                    return (R) result;
                }
            } catch (InterruptedException e) {
                LOGGER.info("Interrupted while waiting for result of script environment task; exiting stack by throwing a runtime exception now");
                throw new RuntimeException("Exit stack", e);
            }
        }

        private void lazilyInitialize() {

            if (scriptEnvThread == null || !scriptEnvThread.isAlive()) {
                // Reset the synchronization stuff
                threadInitializationLatch = new CountDownLatch(1);
                threadSuccessfullyInitialized = false;

                // Start up the thread which provides the environment
                scriptEnvThread = new Thread(new ScriptEnvironmentThreadRunnable(), Consts.MOD_NAME + " script environment thread");
                scriptEnvThread.start();

                try {
                    // Wait for the environment thread to finish its initialization
                    threadInitializationLatch.await();
                } catch (InterruptedException e) {
                    // In case of interruption, we need to bail out of this method
                    throw new RuntimeException("Interrupted while waiting for script environment thread to start up", e);
                }
            }

            // If the thread didn't manage to initialize properly, we are surely not able to invoke any tasks
            if (!threadSuccessfullyInitialized) {
                throw new RuntimeException("Script environment thread didn't manage to initialize properly; cannot execute the task now");
            }
        }

        private class ScriptEnvironmentThreadRunnable implements Runnable {

            private Context    context;
            private Scriptable globalScope;

            @Override
            public void run() {

                try {
                    initializeContext();
                    threadSuccessfullyInitialized = true;
                } finally {
                    threadInitializationLatch.countDown();
                }

                while (true) {
                    try {
                        // Wait for the next task to become available and then immediately invoke it
                        ScriptEnvironmentTask<?> nextTask = taskQueue.take();
                        invokeTaskAndPushResult(nextTask);
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

            private void invokeTaskAndPushResult(ScriptEnvironmentTask<?> task) {

                Object result;
                try {
                    result = task.invoke(context, globalScope);
                } catch (RuntimeException | Error e) {
                    // When the task fails with a RuntimeException or an Error, put that Throwable into the result queue, so that it can be rethrown in the original thread
                    resultQueue.offer(new InvocationThrowableWrapper(e));
                    return;
                }

                // Otherwise, put the result in the result queue, so that the original invocation method can return it to the user
                resultQueue.offer(result == null ? NULL : result);
            }

        }

        private static class InvocationThrowableWrapper {

            private final Throwable throwable;

            private InvocationThrowableWrapper(Throwable throwable) {

                this.throwable = throwable;
            }

        }

    }

}
