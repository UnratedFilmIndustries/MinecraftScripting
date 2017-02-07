
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagIntArray;

public class ScriptTagIntArray extends ScriptTagBase {

    public final NBTTagIntArray arr;

    public ScriptTagIntArray(int[] data) {

        super(new NBTTagIntArray(data));

        arr = (NBTTagIntArray) base;
    }

    public ScriptTagIntArray(NBTTagIntArray arr) {

        super(arr);

        this.arr = arr;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagIntArray(arr.copy());
    }

    public int[] getValue() {

        return arr.getIntArray();
    }

}
