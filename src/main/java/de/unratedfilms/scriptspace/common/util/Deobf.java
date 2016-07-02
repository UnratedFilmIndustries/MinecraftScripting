
package de.unratedfilms.scriptspace.common.util;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ResourceLocation;
import com.mojang.authlib.GameProfile;

public class Deobf {

    public static PositionedSoundRecord PositionedSoundRecord_create(ResourceLocation resource, float pitch) {

        return PositionedSoundRecord.func_147674_a(resource, pitch);
    }

    public static boolean isPlayerOpped(ServerConfigurationManager configurationManager, GameProfile user) {

        return configurationManager.func_152596_g(user);
    }

    private Deobf() {

    }

}
