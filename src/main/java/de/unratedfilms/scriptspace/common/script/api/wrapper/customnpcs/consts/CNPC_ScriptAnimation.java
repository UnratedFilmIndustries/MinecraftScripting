
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class CNPC_ScriptAnimation {

    private static final StringToNativeMapping<Integer> MAPPING;

    static {

        MAPPING = StringToNativeMapping
                .withDefault(0)
                .put("Normal", 0)
                .put("Sitting", 1)
                .put("Lying", 2)
                .put("Hugging", 3)
                .put("Sneaking", 4)
                .put("Aiming", 6)
                .put("Crawling", 7)
                .put("Pointing", 8)
                .put("Crying", 9)
                .put("Waving", 10)
                .put("Bow", 11)
                .put("No", 12)
                .put("Yes", 13);

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

    private CNPC_ScriptAnimation() {}

}
