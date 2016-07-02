
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagFloat;

public class ScriptTagFloat extends ScriptTagBase {

    public final NBTTagFloat tag;

    public ScriptTagFloat(float data) {

        super(new NBTTagFloat(data));

        tag = (NBTTagFloat) base;
    }

    public ScriptTagFloat(NBTTagFloat tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagFloat((NBTTagFloat) tag.copy());
    }

    public float getValue() {

        return tag.func_150288_h();
    }

}
