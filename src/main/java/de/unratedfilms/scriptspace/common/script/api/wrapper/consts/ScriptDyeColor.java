
package de.unratedfilms.scriptspace.common.script.api.wrapper.consts;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;
import net.minecraft.item.EnumDyeColor;

public class ScriptDyeColor {

    private static final ImmutableBiMap<String, EnumDyeColor> MAPPING;

    static {

        Builder<String, EnumDyeColor> builder = ImmutableBiMap.<String, EnumDyeColor> builder();
        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            builder.put(StringUtils.capitalize(dyeColor.getName().replace('_', ' ')), dyeColor);
        }
        MAPPING = builder.build();

    }

    public static final String[] getAll() {

        return new ArrayList<>(MAPPING.keySet()).toArray(new String[0]);
    }

    public static String fromNative(EnumDyeColor dyeColor) {

        return MAPPING.containsValue(dyeColor) ? MAPPING.inverse().get(dyeColor) : fromNative(EnumDyeColor.WHITE);
    }

    public static EnumDyeColor toNative(String dyeColor) {

        return MAPPING.containsKey(dyeColor) ? MAPPING.get(dyeColor) : EnumDyeColor.WHITE;
    }

}
