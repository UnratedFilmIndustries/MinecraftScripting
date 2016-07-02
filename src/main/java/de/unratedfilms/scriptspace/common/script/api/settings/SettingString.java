
package de.unratedfilms.scriptspace.common.script.api.settings;

import org.apache.commons.lang3.Validate;

public class SettingString extends Setting {

    public final String string;

    public SettingString(String name, String displayName) {

        this(name, displayName, "");
    }

    public SettingString(String name, String displayName, String string) {

        super(name, displayName);

        Validate.notNull(string);
        this.string = string;
    }

    @Override
    public Object getValue() {

        return string;
    }

    public SettingString withValue(String newString) {

        return new SettingString(name, displayName, newString);
    }

}
