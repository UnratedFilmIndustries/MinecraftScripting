
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class CNPC_ScriptTacticalBehavior {

    private static final StringToNativeMapping<Integer> MAPPING;

    static {

        MAPPING = StringToNativeMapping
                .withDefault(6)
                .put("None", 6)
                .put("Rush", 0)
                .put("Dodge", 1)
                .put("Surround", 2)
                .put("Hit & run", 3)
                .put("Ambush", 4)
                .put("Stalk", 5);

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

    private CNPC_ScriptTacticalBehavior() {}

}
