
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import noppes.npcs.constants.EnumMovingType;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptMovementBehavior {

    private static final ImmutableBiMap<String, EnumMovingType> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, EnumMovingType> builder()
                .put("Standing", EnumMovingType.Standing)
                .put("Wandering", EnumMovingType.Wandering)
                .put("Moving along path", EnumMovingType.MovingPath)
                .build();

    }

    public static final String[] getAllMovementBehaviors() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(EnumMovingType movementBehavior) {

        return MAPPING.inverse().get(movementBehavior);
    }

    public static EnumMovingType toNative(String movementBehavior) {

        return MAPPING.containsKey(movementBehavior) ? MAPPING.get(movementBehavior) : EnumMovingType.Standing;
    }

}
