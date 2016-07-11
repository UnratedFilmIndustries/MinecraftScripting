
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts;

import java.util.ArrayList;
import com.google.common.collect.ImmutableBiMap;

public class CNPC_ScriptMovementPathBehavior {

    private static final ImmutableBiMap<String, Integer> MAPPING;

    static {

        MAPPING = ImmutableBiMap.<String, Integer> builder()
                .put("Looping", 0)
                .put("Backtracking", 1)
                .build();

    }

    public static final String[] getAllMovementPathBehaviors() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(int movementPathBehavior) {

        return MAPPING.containsValue(movementPathBehavior) ? MAPPING.inverse().get(movementPathBehavior) : fromNative(0);
    }

    public static int toNative(String movementPathBehavior) {

        return MAPPING.containsKey(movementPathBehavior) ? MAPPING.get(movementPathBehavior) : 0;
    }

}
