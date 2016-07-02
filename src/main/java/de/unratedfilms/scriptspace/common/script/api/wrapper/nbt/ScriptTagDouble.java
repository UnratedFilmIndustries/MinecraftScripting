
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagDouble;

public class ScriptTagDouble extends ScriptTagBase {

    public final NBTTagDouble tag;

    public ScriptTagDouble(double data) {

        super(new NBTTagDouble(data));

        tag = (NBTTagDouble) base;
    }

    public ScriptTagDouble(NBTTagDouble tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagDouble((NBTTagDouble) tag.copy());
    }

    public double getValue() {

        return tag.func_150286_g();
    }

}
