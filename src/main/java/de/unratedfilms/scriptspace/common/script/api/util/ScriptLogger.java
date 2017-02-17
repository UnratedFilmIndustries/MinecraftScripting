
package de.unratedfilms.scriptspace.common.script.api.util;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;

public class ScriptLogger {

    public static void trace(String message, Object... params) {

        LOGGER.trace(message, params);
    }

    public static void debug(String message, Object... params) {

        LOGGER.debug(message, params);
    }

    public static void info(String message, Object... params) {

        LOGGER.info(message, params);
    }

    public static void warning(String message, Object... params) {

        LOGGER.warn(message, params);
    }

    public static void error(String message, Object... params) {

        LOGGER.error(message, params);
    }

    public static void fatal(String message, Object... params) {

        LOGGER.fatal(message, params);
    }

}
