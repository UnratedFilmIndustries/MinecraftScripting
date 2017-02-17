
package de.unratedfilms.scriptspace.common.script.api.settings;

import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;

public class SettingItemStack extends Setting {

    public ScriptItemStack stack;

    public SettingItemStack(String name, String displayName) {

        this(name, displayName, null);
    }

    public SettingItemStack(String name, String displayName, ScriptItemStack stack) {

        super(name, displayName);

        this.stack = stack == null ? new ScriptItemStack() : stack;
    }

    @Override
    public Object getValue() {

        return stack;
    }

    public SettingItemStack withValue(ScriptItemStack newStack) {

        return new SettingItemStack(name, displayName, newStack);
    }

}
