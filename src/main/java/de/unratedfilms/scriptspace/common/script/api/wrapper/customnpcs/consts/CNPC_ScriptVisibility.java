
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptVisibility {

    private static final ImmutableBiMap<String, Integer> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Integer> builder()
                .put("Visible", 0)
                .put("Invisible", 1)
                .put("Semi-invisible", 2)
                .build();

    }

    public static final String[] get() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(int visibility) {

        return MAPPING.containsValue(visibility) ? MAPPING.inverse().get(visibility) : fromNative(0);
    }

    public static int toNative(String visibility) {

        return MAPPING.containsKey(visibility) ? MAPPING.get(visibility) : 0;
    }

}
