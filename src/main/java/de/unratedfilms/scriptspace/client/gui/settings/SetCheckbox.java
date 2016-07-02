
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.vanilla.CheckboxVanilla;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBoolean;

public class SetCheckbox extends CheckboxVanilla implements SettingWidget, Shiftable {

    private final SettingBoolean setting;

    public SetCheckbox(SettingBoolean setting) {

        super(setting.displayName, setting.enabled);

        this.setting = setting;
    }

    @Override
    public SettingBoolean applySetting() {

        return setting.withValue(isChecked());
    }

    @Override
    public void shiftY(int dy) {

        y += dy;
    }

}
