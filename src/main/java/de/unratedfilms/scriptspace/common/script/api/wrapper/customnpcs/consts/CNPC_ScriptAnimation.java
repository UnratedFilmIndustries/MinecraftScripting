
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;
import noppes.npcs.constants.EnumAnimation;

public class CNPC_ScriptAnimation {

    private static final ImmutableBiMap<String, EnumAnimation> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, EnumAnimation> builder()
                .put("Normal", EnumAnimation.NONE)
                .put("Sitting", EnumAnimation.SITTING)
                .put("Lying", EnumAnimation.LYING)
                .put("Hugging", EnumAnimation.HUG)
                .put("Sneaking", EnumAnimation.SNEAKING)
                .put("Dancing", EnumAnimation.DANCING)
                .build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(EnumAnimation animation) {

        return MAPPING.containsValue(animation) ? MAPPING.inverse().get(animation) : fromNative(EnumAnimation.NONE);
    }

    public static EnumAnimation toNative(String animation) {

        return MAPPING.containsKey(animation) ? MAPPING.get(animation) : EnumAnimation.NONE;
    }

}
