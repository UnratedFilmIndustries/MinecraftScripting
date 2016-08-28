
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;
import noppes.npcs.constants.EnumStandingType;

public class CNPC_ScriptRotationBehavior {

    private static final ImmutableBiMap<String, EnumStandingType> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, EnumStandingType> builder()
                .put("Free", EnumStandingType.RotateBody)
                .put("Stalking", EnumStandingType.Stalking)
                .put("Fixed body, fixed head", EnumStandingType.NoRotation)
                .put("Fixed body, free head", EnumStandingType.HeadRotation)
                .build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(EnumStandingType rotationBehavior) {

        return MAPPING.inverse().get(rotationBehavior);
    }

    public static EnumStandingType toNative(String rotationBehavior) {

        return MAPPING.containsKey(rotationBehavior) ? MAPPING.get(rotationBehavior) : EnumStandingType.RotateBody;
    }

}
