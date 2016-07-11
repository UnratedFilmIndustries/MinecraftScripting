
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import noppes.npcs.constants.EnumNavType;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptTacticalBehavior {

    private static final ImmutableBiMap<String, EnumNavType> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, EnumNavType> builder()
                .put("None", EnumNavType.None)
                .put("Rush", EnumNavType.Default)
                .put("Dodge", EnumNavType.Dodge)
                .put("Surround", EnumNavType.Surround)
                .put("Hit & run", EnumNavType.HitNRun)
                .put("Ambush", EnumNavType.Ambush)
                .put("Stalk", EnumNavType.Stalk)
                .build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(EnumNavType tacticalBehavior) {

        return MAPPING.inverse().get(tacticalBehavior);
    }

    public static EnumNavType toNative(String tacticalBehavior) {

        return MAPPING.containsKey(tacticalBehavior) ? MAPPING.get(tacticalBehavior) : EnumNavType.None;
    }

}
