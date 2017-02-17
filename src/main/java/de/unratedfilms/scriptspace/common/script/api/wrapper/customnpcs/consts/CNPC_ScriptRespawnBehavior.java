
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptRespawnBehavior {

    private static final ImmutableBiMap<String, Integer> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Integer> builder()
                .put("Always", 0)
                .put("Day", 1)
                .put("Night", 2)
                .put("None", 3)
                .put("Naturally", 4)
                .build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(int respawnBehavior) {

        return MAPPING.containsValue(respawnBehavior) ? MAPPING.inverse().get(respawnBehavior) : fromNative(0);
    }

    public static int toNative(String respawnBehavior) {

        return MAPPING.containsKey(respawnBehavior) ? MAPPING.get(respawnBehavior) : 0;
    }

}
