
package de.unratedfilms.scriptspace.common.script.api.settings;

import net.minecraft.util.MathHelper;

public class SettingInt extends Setting {

    public final int min;
    public final int max;

    public final int value;

    public SettingInt(String name, String displayName) {

        this(name, displayName, 0);
    }

    public SettingInt(String name, String displayName, int value) {

        this(name, displayName, value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public SettingInt(String name, String displayName, int value, int min, int max) {

        super(name, displayName);

        this.value = MathHelper.clamp_int(value, min, max);
        this.min = min;
        this.max = max;
    }

    @Override
    public Object getValue() {

        return value;
    }

    public SettingInt withValue(int newValue) {

        return new SettingInt(name, displayName, newValue, min, max);
    }

}
