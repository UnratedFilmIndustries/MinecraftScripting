
package de.unratedfilms.scriptspace.common.script.api.settings;

public class SettingBoolean extends Setting {

    public final boolean enabled;

    public SettingBoolean(String name, String displayName) {

        this(name, displayName, false);
    }

    public SettingBoolean(String name, String displayName, boolean enabled) {

        super(name, displayName);

        this.enabled = enabled;
    }

    @Override
    public Object getValue() {

        return enabled;
    }

    public SettingBoolean withValue(boolean newEnabled) {

        return new SettingBoolean(name, displayName, newEnabled);
    }

}
