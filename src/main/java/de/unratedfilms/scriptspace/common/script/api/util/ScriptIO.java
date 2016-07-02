
package de.unratedfilms.scriptspace.common.script.api.util;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;

public class ScriptIO {

    public static void logTrace(String message, Object... params) {

        LOGGER.trace(message, params);
    }

    public static void logDebug(String message, Object... params) {

        LOGGER.debug(message, params);
    }

    public static void logInfo(String message, Object... params) {

        LOGGER.info(message, params);
    }

    public static void logWarning(String message, Object... params) {

        LOGGER.warn(message, params);
    }

    public static void logError(String message, Object... params) {

        LOGGER.error(message, params);
    }

    public static void logFatal(String message, Object... params) {

        LOGGER.fatal(message, params);
    }

    /*
     * Compatibility methods
     */

    public static void print(String string) {

        logInfo(string);
    }

    public static void println(String string) {

        logInfo(string);
    }

}
