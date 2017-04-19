
package de.unratedfilms.scriptspace.common.script.api.wrapper.consts;

import org.apache.commons.lang3.StringUtils;
import net.minecraft.item.EnumDyeColor;
import de.unratedfilms.scriptspace.common.util.StringToNativeMapping;

public class ScriptDyeColor {

    private static final StringToNativeMapping<EnumDyeColor> MAPPING;

    static {

        MAPPING = StringToNativeMapping.withDefault(EnumDyeColor.WHITE);

        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            MAPPING.put(StringUtils.capitalize(dyeColor.getName().replace('_', ' ')), dyeColor);
        }

    }

    public static final String[] getAll() {

        return MAPPING.getAll();
    }

    public static String fromNative(EnumDyeColor nativee) {

        return MAPPING.fromNative(nativee);
    }

    public static EnumDyeColor toNative(String string) {

        return MAPPING.toNative(string);
    }

    private ScriptDyeColor() {}

}
