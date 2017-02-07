
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagInt;

public class ScriptTagInt extends ScriptTagBase {

    public final NBTTagInt tag;

    public ScriptTagInt(int data) {

        super(new NBTTagInt(data));

        tag = (NBTTagInt) base;
    }

    public ScriptTagInt(NBTTagInt tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagInt(tag.copy());
    }

    public int getValue() {

        return tag.getInt();
    }

}
