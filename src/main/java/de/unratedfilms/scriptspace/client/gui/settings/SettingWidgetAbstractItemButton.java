
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.Arrays;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.guilib.widgets.view.impl.ButtonItemImpl;
import de.unratedfilms.guilib.widgets.view.impl.TextTooltipImpl;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.GiveItemStackServerMessage;

public abstract class SettingWidgetAbstractItemButton extends ButtonItemImpl implements SettingWidget {

    protected final String label;

    private final int      xShift;

    public SettingWidgetAbstractItemButton(String label, ItemStack item, ButtonHandler handler) {

        super(item, handler);

        this.label = label;
        xShift = MC.fontRenderer.getStringWidth(label) + 10;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {

        super.setItemStack(itemStack);
        zLevel = itemStack.getItem() != null && itemStack.hasEffect(0) ? 50 : 0;
    }

    @Override
    public void setPosition(int x, int y) {

        super.setPosition(x + xShift, y);
    }

    @Override
    protected TextTooltipImpl generateTooltip() {

        TextTooltipImpl tooltip = super.generateTooltip();

        tooltip.addLines(Arrays.asList(I18n.format("gui.scriptspace.itemButton.give")));

        return tooltip;
    }

    @Override
    public void draw(int mx, int my) {

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        MC.fontRenderer.drawString(label, x - xShift, y + 5, 0xffffff);
        super.draw(mx, my);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        drawRect(x, y, x + width, y + 1, 0xff000000);
        drawRect(x, y + height - 1, x + width, y + height, 0xff000000);
        drawRect(x, y, x + 1, y + height, 0xff000000);
        drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    protected class Handler implements ButtonHandler {

        @Override
        public void buttonClicked(Button button, MouseButton mouseButton) {

            if (mouseButton == MouseButton.MIDDLE || mouseButton == MouseButton.RIGHT) {
                NetworkService.DISPATCHER.sendToServer(new GiveItemStackServerMessage(getItemStack()));
            }
        }

    }

}
