
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptShelteringBehavior {

    private static final ImmutableBiMap<String, Integer> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Integer> builder()
                .put("Disabled", 2)
                .put("Darkness", 0)
                .put("Sunlight", 1)
                .build();

    }

    public static final String[] getAllShelteringBehaviors() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(int shelteringBehavior) {

        return MAPPING.containsValue(shelteringBehavior) ? MAPPING.inverse().get(shelteringBehavior) : fromNative(2);
    }

    public static int toNative(String shelteringBehavior) {

        return MAPPING.containsKey(shelteringBehavior) ? MAPPING.get(shelteringBehavior) : 2;
    }

}
