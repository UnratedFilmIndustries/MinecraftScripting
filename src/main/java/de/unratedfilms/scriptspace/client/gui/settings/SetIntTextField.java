
package de.unratedfilms.scriptspace.client.gui.settings;

import org.apache.commons.lang3.math.NumberUtils;
import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.vanilla.TextFieldVanilla;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingInt;

public class SetIntTextField extends TextFieldVanilla implements SettingWidget, Shiftable {

    private final SettingInt setting;

    private final int        xShift;

    public SetIntTextField(SettingInt setting) {

        super(60, 14, new IntegerNumberFilter());

        setMaxLength(15);
        setText("" + setting.value);
        this.setting = setting;

        xShift = MC.fontRenderer.getStringWidth(setting.displayName) + 10;
    }

    @Override
    public SettingInt applySetting() {

        int value = NumberUtils.toInt(getText(), setting.value);
        return setting.withValue(value);
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
