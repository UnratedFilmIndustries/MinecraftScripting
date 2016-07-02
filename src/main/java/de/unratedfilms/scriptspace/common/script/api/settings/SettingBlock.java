
package de.unratedfilms.scriptspace.common.script.api.settings;

import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;

public class SettingBlock extends Setting {

    public final ScriptBlock block;

    public SettingBlock(String name, String displayName) {

        this(name, displayName, null);
    }

    public SettingBlock(String name, String displayName, ScriptBlock block) {

        super(name, displayName);

        this.block = block == null ? ScriptBlock.forName("air") : block;
    }

    @Override
    public Object getValue() {

        return block;
    }

    public SettingBlock withValue(ScriptBlock newBlock) {

        return new SettingBlock(name, displayName, newBlock);
    }

}
