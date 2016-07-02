
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;

public class ScriptTagCompound extends ScriptTagBase {

    public final NBTTagCompound tag;

    public ScriptTagCompound() {

        super(new NBTTagCompound());

        tag = (NBTTagCompound) base;
    }

    public ScriptTagCompound(NBTTagCompound tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagCompound((NBTTagCompound) tag.copy());
    }

    // -------------------------SETS-------------------------

    public void setTag(String name, ScriptTagBase tag) {

        this.tag.setTag(name, tag.base);
    }

    public void setByte(String name, byte data) {

        tag.setByte(name, data);
    }

    public void setShort(String name, short data) {

        tag.setShort(name, data);
    }

    public void setInteger(String name, int data) {

        tag.setInteger(name, data);
    }

    public void setLong(String name, long data) {

        tag.setLong(name, data);
    }

    public void setFloat(String name, float data) {

        tag.setFloat(name, data);
    }

    public void setDouble(String name, double data) {

        tag.setDouble(name, data);
    }

    public void setString(String name, String data) {

        tag.setString(name, data);
    }

    public void setByteArray(String name, byte[] data) {

        tag.setByteArray(name, data);
    }

    public void setIntArray(String name, int[] data) {

        tag.setIntArray(name, data);
    }

    public void setTagList(String name, ScriptTagList tag) {

        this.tag.setTag(name, tag.base);
    }

    public void setCompoundTag(String name, ScriptTagCompound tag) {

        this.tag.setTag(name, tag.base);
    }

    public void setBoolean(String name, boolean data) {

        tag.setBoolean(name, data);
    }

    // -------------------------GETS-------------------------

    public ScriptTagBase getTag(String name) {

        return ScriptTagBase.createFromNative(tag.getTag(name));
    }

    public byte getByte(String name) throws ReportedException {

        return tag.getByte(name);
    }

    public short getShort(String name) throws ReportedException {

        return tag.getShort(name);
    }

    public int getInteger(String name) throws ReportedException {

        return tag.getInteger(name);
    }

    public long getLong(String name) throws ReportedException {

        return tag.getLong(name);
    }

    public float getFloat(String name) throws ReportedException {

        return tag.getFloat(name);
    }

    public double getDouble(String name) throws ReportedException {

        return tag.getDouble(name);
    }

    public String getString(String name) throws ReportedException {

        return tag.getString(name);
    }

    public byte[] getByteArray(String name) throws ReportedException {

        return tag.getByteArray(name);
    }

    public int[] getIntArray(String name) throws ReportedException {

        return tag.getIntArray(name);
    }

    public ScriptTagCompound getCompoundTag(String name) throws ReportedException {

        return new ScriptTagCompound(tag.getCompoundTag(name));
    }

    public ScriptTagList getTagList(String name) throws ReportedException {

        NBTBase list = tag.getTag(name);
        if (! (list instanceof NBTTagList)) {
            list = new NBTTagList();
        }
        return new ScriptTagList((NBTTagList) list);
    }

    public boolean getBoolean(String name) throws ReportedException {

        return tag.getBoolean(name);
    }

    public boolean hasTag(String name) {

        return tag.hasKey(name);
    }

    public void removeTag(String name) {

        tag.removeTag(name);
    }

    public boolean hasNoTags() {

        return tag.hasNoTags();
    }

}
