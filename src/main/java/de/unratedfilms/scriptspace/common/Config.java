
package de.unratedfilms.scriptspace.common;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

//TODO Script timeout option?
public class Config {

    public static String[] scriptDirectories;

    public static void initialize(File configFile) {

        Configuration config = new Configuration(configFile);

        syncConfig(config);

        if (config.hasChanged()) {
            config.save();
        }
    }

    private static void syncConfig(Configuration config) {

        scriptDirectories = config.getStringList("directories", "scripts.source", new String[] { "mods/" + Consts.MOD_ID + "/scripts" },
                "The directories which are recursively searched for scripts; they are created if they don't exist yet.");
    }

}
