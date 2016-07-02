
package de.unratedfilms.scriptspace.common.script.env;

import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.unratedfilms.scriptspace.common.script.api.js.Range;
import de.unratedfilms.scriptspace.common.script.api.js.TagItr;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBlock;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBoolean;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingFloat;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingInt;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingItemStack;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingString;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingStringList;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptArray;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptIO;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec2;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.ScriptRandom;
import de.unratedfilms.scriptspace.common.script.api.wrapper.ScriptSelection;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.CNPC_ScriptAnimation;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.CNPC_ScriptRotationBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntity;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagByte;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagByteArray;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagCompound;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagDouble;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagFloat;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagInt;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagIntArray;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagList;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagLong;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagShort;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagString;
import de.unratedfilms.scriptspace.common.script.api.wrapper.tileentity.ScriptTileEntity;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItem;

class ContextConfigurator {

    private static final ImmutableList<Class<? extends Scriptable>> DEFINED_CLASSES;
    private static final ImmutableMap<String, Class<?>>             ABBREVIATIONS;

    static {

        DEFINED_CLASSES = ImmutableList.<Class<? extends Scriptable>> builder()

                .add(Range.class)
                .add(TagItr.class)

                .build();

        ABBREVIATIONS = ImmutableMap.<String, Class<?>> builder()

                // "settings" package
                .put("SettingBlock", SettingBlock.class)
                .put("SettingBoolean", SettingBoolean.class)
                .put("SettingFloat", SettingFloat.class)
                .put("SettingInt", SettingInt.class)
                .put("SettingItemStack", SettingItemStack.class)
                .put("SettingString", SettingString.class)
                .put("SettingStringList", SettingStringList.class)

                // "util" package
                .put("Array", ScriptArray.class)
                .put("IO", ScriptIO.class)
                .put("Vec2", ScriptVec2.class)
                .put("Vec3", ScriptVec3.class)

                // "wrapper" package
                .put("Rand", ScriptRandom.class)
                .put("Selection", ScriptSelection.class)

                // "wrapper.customnpcs" package
                .put("CNPC_Animation", CNPC_ScriptAnimation.class)
                .put("CNPC_RotationBehavior", CNPC_ScriptRotationBehavior.class)

                // "wrapper.entity" package
                .put("Entity", ScriptEntity.class)

                // "wrapper.nbt" package
                .put("TagByte", ScriptTagByte.class)
                .put("TagByteArray", ScriptTagByteArray.class)
                .put("TagCompound", ScriptTagCompound.class)
                .put("TagDouble", ScriptTagDouble.class)
                .put("TagFloat", ScriptTagFloat.class)
                .put("TagInt", ScriptTagInt.class)
                .put("TagIntArray", ScriptTagIntArray.class)
                .put("TagList", ScriptTagList.class)
                .put("TagLong", ScriptTagLong.class)
                .put("TagShort", ScriptTagShort.class)
                .put("TagString", ScriptTagString.class)

                // "wrapper.tileentity" package
                .put("TileEntity", ScriptTileEntity.class)

                // "wrapper.world" package
                .put("Block", ScriptBlock.class)
                .put("Item", ScriptItem.class)

                .build();

    }

    public static void configureContext(Context context, Scriptable globalScope) {

        // Define classes
        try {
            for (Class<? extends Scriptable> clazz : DEFINED_CLASSES) {
                ScriptableObject.defineClass(globalScope, clazz);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Error while defining classes", e);
        }

        // Define abbreviations
        for (Entry<String, Class<?>> entry : ABBREVIATIONS.entrySet()) {
            context.evaluateString(globalScope, entry.getKey() + " = " + "Packages." + entry.getValue().getName(), "<abbreviations>", 1, null);
        }
    }

    private ContextConfigurator() {

    }

}
