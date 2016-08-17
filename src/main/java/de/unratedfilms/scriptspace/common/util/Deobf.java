
package de.unratedfilms.scriptspace.common.util;

import net.minecraft.server.management.ServerConfigurationManager;
import com.mojang.authlib.GameProfile;

public class Deobf {

    public static boolean isPlayerOpped(ServerConfigurationManager configurationManager, GameProfile user) {

        return configurationManager.func_152596_g(user);
    }

    private Deobf() {

    }

}
