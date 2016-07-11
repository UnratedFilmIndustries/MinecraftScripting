
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptRetaliationBehavior {

    private static final ImmutableBiMap<String, Integer> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Integer> builder()
                .put("Normal", 0)
                .put("Panic", 1)
                .put("Retreat", 2)
                .put("None", 3)
                .build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(int retaliationBehavior) {

        return MAPPING.containsValue(retaliationBehavior) ? MAPPING.inverse().get(retaliationBehavior) : fromNative(0);
    }

    public static int toNative(String retaliationBehavior) {

        return MAPPING.containsKey(retaliationBehavior) ? MAPPING.get(retaliationBehavior) : 0;
    }

}
