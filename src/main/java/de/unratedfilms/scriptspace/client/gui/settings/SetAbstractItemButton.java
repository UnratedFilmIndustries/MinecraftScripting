
package de.unratedfilms.scriptspace.client.gui.settings;

import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.vanilla.items.ItemButton;

public abstract class SetAbstractItemButton extends ItemButton implements SettingWidget, Shiftable {

    protected final String label;

    private final int      xShift;

    public SetAbstractItemButton(String label, ItemStack item, ButtonHandler handler) {

        super(item, handler);

        this.label = label;
        xShift = MC.fontRenderer.getStringWidth(label) + 10;
    }

    @Override
    public void setItem(ItemStack item) {

        super.setItem(item);
        zLevel = item.getItem() != null && item.hasEffect(0) ? 50 : 0;
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

        MC.fontRenderer.drawString(label, x - xShift, y + 5, 0xffffff);
        super.draw(mx, my);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        drawRect(x, y, x + width, y + 1, 0xff000000);
        drawRect(x, y + height - 1, x + width, y + height, 0xff000000);
        drawRect(x, y, x + 1, y + height, 0xff000000);
        drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

}
