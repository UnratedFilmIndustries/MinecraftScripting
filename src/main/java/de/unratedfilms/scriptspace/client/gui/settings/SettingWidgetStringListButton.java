
package de.unratedfilms.scriptspace.client.gui.settings;

import net.minecraft.client.Minecraft;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.guilib.widgets.view.impl.ButtonLabelImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingStringList;

public class SettingWidgetStringListButton extends ButtonLabelImpl implements SettingWidget {

    private final SettingStringList setting;

    private final int               xShift;

    public SettingWidgetStringListButton(SettingStringList setting) {

        super(Minecraft.getMinecraft().fontRenderer.getStringWidth(setting.selected) + 8, 15, setting.selected, null);
        setHandler(new Handler());

        this.setting = setting;

        xShift = MC.fontRenderer.getStringWidth(setting.displayName) + 10;
    }

    @Override
    public SettingStringList applySetting() {

        return setting.withValue(getLabel());
    }

    public void setSelectedText(String text) {

        setLabel(text);
        setWidth(MC.fontRenderer.getStringWidth(text) + 8);
    }

    @Override
    public void setPosition(int x, int y) {

        super.setPosition(x + xShift, y);
    }

    @Override
    public void draw(int mx, int my) {

        MC.fontRenderer.drawString(setting.displayName, getX() - xShift, getY() + 3, 0xffffff);
        super.draw(mx, my);
    }

    private class Handler extends LeftButtonHandler {

        // Note that button == SetStringListButton.this
        @Override
        public void leftButtonClicked(Button button) {

            MC.displayGuiScreen(new PopupDropdown(SettingWidgetStringListButton.this, setting.options, (ConfigureProgramScreen) MC.currentScreen));
        }

    }

}
