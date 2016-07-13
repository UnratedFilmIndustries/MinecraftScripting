
package de.unratedfilms.scriptspace.common.script.api.settings;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class SettingStringList extends Setting {

    public final String[] options;

    public final String   selected;

    public SettingStringList(String name, String displayName, String[] options) {

        this(name, displayName, options, null);
    }

    public SettingStringList(String name, String displayName, String[] options, String selected) {

        super(name, displayName);

        Validate.isTrue(options.length > 0, "Must have at least 1 option");

        this.options = options;
        this.selected = !ArrayUtils.contains(options, selected) ? options[0] : selected;
    }

    @Override
    public Object getValue() {

        return selected;
    }

    public SettingStringList withValue(String newSelected) {

        return new SettingStringList(name, displayName, options, newSelected);
    }

}
