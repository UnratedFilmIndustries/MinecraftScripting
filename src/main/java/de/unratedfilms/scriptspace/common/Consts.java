
package de.unratedfilms.scriptspace.common;

import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consts {

    public static final String MOD_ID       = "scriptspace";
    public static final String MOD_NAME     = "ScriptSpace";
    public static String       MOD_VERSION;                                  // dynamic "constant"

    public static final Logger LOGGER       = LogManager.getLogger(MOD_NAME);

    public static final String ROOT_PACKAGE = "de.unratedfilms." + MOD_ID;

    public static Path         MINECRAFT_DIR;                                // dynamic "constant"

    public static boolean      HASMOD_CUSTOM_NPCS;                           // dynamic "constant"

    private Consts() {

    }

}
