
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagShort;

public class ScriptTagShort extends ScriptTagBase {

    public final NBTTagShort tag;

    public ScriptTagShort(short data) {

        super(new NBTTagShort(data));

        tag = (NBTTagShort) base;
    }

    public ScriptTagShort(NBTTagShort tag) {

        super(tag);

        this.tag = tag;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagShort((NBTTagShort) tag.copy());
    }

    public short getValue() {

        return tag.func_150289_e();
    }

}
