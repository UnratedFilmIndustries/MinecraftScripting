
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

/**
 * Base class for NBTTag wrapping.
 * Note that there is no wrapper for NBTTagEnd, as it is only used for tag IO.
 */
public abstract class ScriptTagBase {

    public static ScriptTagBase createFromNative(NBTBase base) {

        if (base instanceof NBTTagByte) {
            return new ScriptTagByte((NBTTagByte) base);
        }
        if (base instanceof NBTTagByteArray) {
            return new ScriptTagByteArray((NBTTagByteArray) base);
        }
        if (base instanceof NBTTagCompound) {
            return new ScriptTagCompound((NBTTagCompound) base);
        }
        if (base instanceof NBTTagDouble) {
            return new ScriptTagDouble((NBTTagDouble) base);
        }
        if (base instanceof NBTTagFloat) {
            return new ScriptTagFloat((NBTTagFloat) base);
        }
        if (base instanceof NBTTagInt) {
            return new ScriptTagInt((NBTTagInt) base);
        }
        if (base instanceof NBTTagIntArray) {
            return new ScriptTagIntArray((NBTTagIntArray) base);
        }
        if (base instanceof NBTTagList) {
            return new ScriptTagList((NBTTagList) base);
        }
        if (base instanceof NBTTagLong) {
            return new ScriptTagLong((NBTTagLong) base);
        }
        if (base instanceof NBTTagShort) {
            return new ScriptTagShort((NBTTagShort) base);
        }
        if (base instanceof NBTTagString) {
            return new ScriptTagString((NBTTagString) base);
        }
        return null;
    }

    protected final NBTBase base;

    public ScriptTagBase(NBTBase base) {

        this.base = base;
    }

    public abstract ScriptTagBase copy();

    @Override
    public String toString() {

        return base.toString();
    }

    @Override
    public int hashCode() {

        return base.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ScriptTagBase) {
            return ((ScriptTagBase) obj).base.equals(base);
        }
        return false;
    }

}
