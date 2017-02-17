
package de.unratedfilms.scriptspace.common.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.management.ServerConfigurationManager;

public class Deobf {

    public static boolean isPlayerOpped(ServerConfigurationManager configurationManager, GameProfile user) {

        return configurationManager.func_152596_g(user);
    }

    private Deobf() {

    }

}
