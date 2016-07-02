
package de.unratedfilms.scriptspace.common.script.api.wrapper.nbt;

import net.minecraft.nbt.NBTTagString;

public class ScriptTagString extends ScriptTagBase {

    public final NBTTagString str;

    public ScriptTagString(String data) {

        super(new NBTTagString(data));

        str = (NBTTagString) base;
    }

    public ScriptTagString(NBTTagString str) {

        super(str);

        this.str = str;
    }

    @Override
    public ScriptTagBase copy() {

        return new ScriptTagString((NBTTagString) str.copy());
    }

    public String getValue() {

        return str.func_150285_a_();
    }

}
