
package de.unratedfilms.scriptspace.common.script.api.wrapper.consts;

import org.apache.commons.lang3.StringUtils;
import net.minecraft.inventory.EntityEquipmentSlot;
import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class ScriptEntityEquipmentSlot {

    private static final StringToNativeMapping<EntityEquipmentSlot> MAPPING;

    static {

        MAPPING = StringToNativeMapping.withDefault(EntityEquipmentSlot.MAINHAND);

        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            MAPPING.put(StringUtils.capitalize(slot.getName()), slot);
        }

    }

    public static final String[] getAll() {

        return MAPPING.getAll();
    }

    public static String fromNative(EntityEquipmentSlot nativee) {

        return MAPPING.fromNative(nativee);
    }

    public static EntityEquipmentSlot toNative(String string) {

        return MAPPING.toNative(string);
    }

    private ScriptEntityEquipmentSlot() {}

}
