
package de.unratedfilms.scriptspace.common.script.api.settings;

public class SettingFloat extends Setting {

    public final float min;
    public final float max;

    public final float value;

    public SettingFloat(String name, String displayName) {

        this(name, displayName, 0);
    }

    public SettingFloat(String name, String displayName, float value) {

        this(name, displayName, value, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public SettingFloat(String name, String displayName, float value, float min, float max) {

        super(name, displayName);

        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public Object getValue() {

        return value;
    }

    public SettingFloat withValue(float newValue) {

        return new SettingFloat(name, displayName, newValue, min, max);
    }

}
