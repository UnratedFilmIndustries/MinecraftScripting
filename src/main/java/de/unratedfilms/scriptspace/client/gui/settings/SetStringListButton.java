
package de.unratedfilms.scriptspace.client.gui.settings;

import net.minecraft.client.Minecraft;
import de.unratedfilms.guilib.core.Button;
import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.vanilla.ButtonVanilla;
import de.unratedfilms.scriptspace.client.gui.settings.widgets.PopupDropdown;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingStringList;

public class SetStringListButton extends ButtonVanilla implements SettingWidget, Shiftable {

    private final SettingStringList setting;

    private final int               xShift;

    public SetStringListButton(SettingStringList setting) {

        super(Minecraft.getMinecraft().fontRenderer.getStringWidth(setting.selected) + 8, 15, setting.selected, null);
        handler = new Handler();

        this.setting = setting;

        xShift = MC.fontRenderer.getStringWidth(setting.displayName) + 10;
    }

    @Override
    public SettingStringList applySetting() {

        return setting.withValue(getLabel());
    }

    public void setSelectedText(String text) {

        setLabel(text);
        width = MC.fontRenderer.getStringWidth(text) + 8;
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
    public void draw(int mx, int my) {

        MC.fontRenderer.drawString(setting.displayName, x - xShift, y + 3, 0xffffff);
        super.draw(mx, my);
    }

    private class Handler extends LeftButtonHandler {

        // Note that button == SetStringListButton.this
        @Override
        public void leftButtonClicked(Button button) {

            MC.displayGuiScreen(new PopupDropdown(SetStringListButton.this, setting.options, (ConfigureProgramScreen) MC.currentScreen));
        }

    }

}
