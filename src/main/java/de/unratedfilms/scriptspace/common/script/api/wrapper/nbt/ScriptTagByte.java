
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagByte;

public class ScriptTagByte extends ScriptTagBase {

    public final NBTTagByte tag;

    public ScriptTagByte(byte data) {

        super(new NBTTagByte(data));

        tag = (NBTTagByte) base;
    }

    public ScriptTagByte(NBTTagByte tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagByte((NBTTagByte) tag.copy());
    }

    public byte getValue() {

        return tag.func_150290_f();
    }

}
