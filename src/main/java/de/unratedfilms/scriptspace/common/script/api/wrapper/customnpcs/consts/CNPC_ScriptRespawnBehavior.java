
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class CNPC_ScriptRespawnBehavior {

    private static final StringToNativeMapping<Integer> MAPPING;

    static {

        MAPPING = StringToNativeMapping
                .withDefault(0)
                .put("Always", 0)
                .put("Day", 1)
                .put("Night", 2)
                .put("None", 3)
                .put("Naturally", 4);

    }

    public static final String[] getAll() {

        return MAPPING.getAll();
    }

    public static String fromNative(int nativee) {

        return MAPPING.fromNative(nativee);
    }

    public static int toNative(String string) {

        return MAPPING.toNative(string);
    }

    private CNPC_ScriptRespawnBehavior() {}

}
