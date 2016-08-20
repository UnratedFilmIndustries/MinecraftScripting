
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.widgets.view.impl.CheckboxImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBoolean;

public class SettingWidgetCheckbox extends CheckboxImpl implements SettingWidget {

    private final SettingBoolean setting;

    public SettingWidgetCheckbox(SettingBoolean setting) {

        super(setting.displayName, setting.enabled);

        this.setting = setting;
    }

    @Override
    public SettingBoolean applySetting() {

        return setting.withValue(isChecked());
    }

}
