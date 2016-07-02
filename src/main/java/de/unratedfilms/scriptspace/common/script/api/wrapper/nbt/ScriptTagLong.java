
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagLong;

public class ScriptTagLong extends ScriptTagBase {

    public final NBTTagLong tag;

    public ScriptTagLong(long data) {

        super(new NBTTagLong(data));

        tag = (NBTTagLong) base;
    }

    public ScriptTagLong(NBTTagLong tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagLong((NBTTagLong) tag.copy());
    }

    public long getValue() {

        return tag.func_150291_c();
    }

}
