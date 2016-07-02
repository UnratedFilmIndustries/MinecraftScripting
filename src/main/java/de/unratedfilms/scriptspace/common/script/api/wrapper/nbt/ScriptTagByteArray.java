
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagByteArray;

public class ScriptTagByteArray extends ScriptTagBase {

    public final NBTTagByteArray arr;

    public ScriptTagByteArray(byte[] data) {

        super(new NBTTagByteArray(data));

        arr = (NBTTagByteArray) base;
    }

    public ScriptTagByteArray(NBTTagByteArray arr) {

        super(arr);

        this.arr = arr;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagByteArray((NBTTagByteArray) arr.copy());
    }

    public byte[] getValue() {

        return arr.func_150292_c();
    }

}
