
package de.unratedfilms.scriptspace.common.script.api.settings;

import org.apache.commons.lang3.Validate;

public abstract class Setting {

    public final String name;
    public final String displayName;

    protected Setting(String name, String displayName) {

        Validate.notNull(name);
        Validate.notNull(displayName);

        this.name = name;
        this.displayName = displayName;
    }

    public abstract Object getValue();

}
