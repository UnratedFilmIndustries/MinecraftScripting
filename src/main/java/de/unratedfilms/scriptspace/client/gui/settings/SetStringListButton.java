
package de.unratedfilms.scriptspace.client.gui.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.vanilla.ButtonVanilla;
import de.unratedfilms.scriptspace.client.gui.settings.widgets.PopupDropdown;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingStringList;
import de.unratedfilms.scriptspace.common.util.Deobf;

public class SetStringListButton extends ButtonVanilla implements SettingWidget, Shiftable {

    private final SettingStringList setting;

    private final int               xShift;

    public SetStringListButton(SettingStringList setting) {

        super(Minecraft.getMinecraft().fontRenderer.getStringWidth(setting.selected) + 8, 15, setting.selected, null);

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

    @Override
    public void handleClick(int mx, int my) {

        MC.getSoundHandler().playSound(Deobf.PositionedSoundRecord_create(new ResourceLocation("gui.button.press"), 1.0F));
        MC.displayGuiScreen(new PopupDropdown(this, setting.options, (ConfigureProgramScreen) MC.currentScreen));
    }

}
