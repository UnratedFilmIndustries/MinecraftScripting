
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.widgets.view.impl.TextFieldImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingString;

public class SettingWidgetStringTextField extends TextFieldImpl implements SettingWidget {

    private final SettingString setting;

    private final int           xShift;

    public SettingWidgetStringTextField(SettingString setting) {

        super(200, 14, new VanillaFilter());

        setMaxLength(100);
        setText(setting.string);
        this.setting = setting;

        xShift = MC.fontRenderer.getStringWidth(setting.displayName) + 10;
    }

    @Override
    public SettingString applySetting() {

        return setting.withValue(getText());
    }

    @Override
    public void setPosition(int x, int y) {

        super.setPosition(x + xShift, y);
    }

    @Override
    protected void drawBackground() {

        MC.fontRenderer.drawString(setting.displayName, getX() - xShift, getY() + 3, 0xffffff);
        super.drawBackground();
    }

}
