
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class CNPC_ScriptVisibility {

    private static final StringToNativeMapping<Integer> MAPPING;

    static {

        MAPPING = StringToNativeMapping
                .withDefault(0)
                .put("Visible", 0)
                .put("Invisible", 1)
                .put("Semi-invisible", 2);

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

    private CNPC_ScriptVisibility() {}

}
