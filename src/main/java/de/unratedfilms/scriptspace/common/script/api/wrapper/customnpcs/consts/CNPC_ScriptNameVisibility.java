
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptNameVisibility {

    private static final ImmutableBiMap<String, Integer> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Integer> builder()
                .put("Visible", 0)
                .put("Invisible", 1)
                .put("When attacking", 2)
                .build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(int nameVisibility) {

        return MAPPING.containsValue(nameVisibility) ? MAPPING.inverse().get(nameVisibility) : fromNative(0);
    }

    public static int toNative(String nameVisibility) {

        return MAPPING.containsKey(nameVisibility) ? MAPPING.get(nameVisibility) : 0;
    }

}
