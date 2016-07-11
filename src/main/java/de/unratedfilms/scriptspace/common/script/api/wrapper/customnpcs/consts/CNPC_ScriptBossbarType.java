
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptBossbarType {

    private static final ImmutableBiMap<String, Byte> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Byte> builder()
                .put("Invisible", (byte) 0)
                .put("Visible", (byte) 1)
                .put("When attacking", (byte) 2)
                .build();

    }

    public static final String[] getBossbarTypes() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(byte bossbarType) {

        return MAPPING.containsValue(bossbarType) ? MAPPING.inverse().get(bossbarType) : fromNative((byte) 0);
    }

    public static byte toNative(String bossbarType) {

        return MAPPING.containsKey(bossbarType) ? MAPPING.get(bossbarType) : 0;
    }

}
