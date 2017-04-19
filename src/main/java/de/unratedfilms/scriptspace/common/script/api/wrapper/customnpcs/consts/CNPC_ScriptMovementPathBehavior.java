
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class CNPC_ScriptMovementPathBehavior {

    private static final StringToNativeMapping<Integer> MAPPING;

    static {

        MAPPING = StringToNativeMapping
                .withDefault(0)
                .put("Looping", 0)
                .put("Backtracking", 1);

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

    private CNPC_ScriptMovementPathBehavior() {}

}
