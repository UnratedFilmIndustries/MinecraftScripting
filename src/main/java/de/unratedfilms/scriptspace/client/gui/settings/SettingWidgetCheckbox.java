
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.widgets.model.Checkbox;
import de.unratedfilms.guilib.widgets.view.impl.CheckboxImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBoolean;

public class SettingWidgetCheckbox extends SettingWidget<SettingBoolean> {

    private final Checkbox checkbox;

    public SettingWidgetCheckbox(SettingBoolean setting) {

        super(setting);

        checkbox = new CheckboxImpl(null, setting.enabled);
        settingContainer.addWidget(checkbox);
    }

    @Override
    public SettingBoolean applySetting() {

        return setting.withValue(checkbox.isChecked());
    }

}
