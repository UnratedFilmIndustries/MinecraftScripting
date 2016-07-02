
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ScriptTagList extends ScriptTagBase {

    public static ScriptTagList newFloatList(float... list) {

        NBTTagList tagList = new NBTTagList();
        for (float f : list) {
            tagList.appendTag(new NBTTagFloat(f));
        }
        return new ScriptTagList(tagList);
    }

    public static ScriptTagList newDoubleList(double... list) {

        NBTTagList tagList = new NBTTagList();
        for (double d : list) {
            tagList.appendTag(new NBTTagDouble(d));
        }
        return new ScriptTagList(tagList);
    }

    public final NBTTagList list;

    public ScriptTagList() {

        super(new NBTTagList());

        list = (NBTTagList) base;
    }

    public ScriptTagList(NBTTagList list) {

        super(list);

        this.list = list;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagList((NBTTagList) list.copy());
    }

    public void appendTag(ScriptTagBase tag) {

        list.appendTag(tag.base);
    }

    public ScriptTagBase removeTag(int index) {

        return ScriptTagBase.createFromNative(list.removeTag(index));
    }

    // TODO Improve/cache field?
    public ScriptTagBase tagAt(int index) {

        List<NBTBase> tagList = ReflectionHelper.getPrivateValue(NBTTagList.class, list, 0);
        return ScriptTagBase.createFromNative(tagList.get(index));
    }

    public int tagCount() {

        return list.tagCount();
    }

}
