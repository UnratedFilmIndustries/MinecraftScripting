
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.vanilla.TextFieldVanilla;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingString;

public class SetStringTextField extends TextFieldVanilla implements SettingWidget, Shiftable {

    private final SettingString setting;

    private final int           xShift;

    public SetStringTextField(SettingString setting) {

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
    public void shiftY(int dy) {

        y += dy;
    }

    @Override
    protected void drawBackground() {

        MC.fontRenderer.drawString(setting.displayName, x - xShift, y + 3, 0xffffff);
        super.drawBackground();
    }

}
