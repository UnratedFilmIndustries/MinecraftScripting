
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.Locale;
import org.apache.commons.lang3.math.NumberUtils;
import de.unratedfilms.guilib.widgets.view.impl.TextFieldImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingFloat;

public class SettingWidgetFloatTextField extends TextFieldImpl implements SettingWidget {

    private final SettingFloat setting;

    private final int          xShift;

    public SettingWidgetFloatTextField(SettingFloat setting) {

        super(60, 14, new DecimalNumberFilter());

        setMaxLength(15);
        setText(String.format(Locale.ENGLISH, "%.3f", setting.value));
        this.setting = setting;

        xShift = MC.fontRenderer.getStringWidth(setting.displayName) + 10;
    }

    @Override
    public SettingFloat applySetting() {

        float value = NumberUtils.toFloat(getText(), setting.value);
        return setting.withValue(value);
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
