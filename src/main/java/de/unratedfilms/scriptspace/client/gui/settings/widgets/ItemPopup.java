
package de.unratedfilms.scriptspace.client.gui.settings.widgets;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import de.unratedfilms.guilib.basic.BasicScreen;
import de.unratedfilms.guilib.core.Button;
import de.unratedfilms.guilib.core.Button.LeftButtonHandler;
import de.unratedfilms.guilib.core.Container;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.core.Scrollbar;
import de.unratedfilms.guilib.core.Widget;
import de.unratedfilms.guilib.vanilla.ScrollbarVanilla;
import de.unratedfilms.guilib.vanilla.items.ItemButton;
import de.unratedfilms.scriptspace.client.gui.CustomOverlay;
import de.unratedfilms.scriptspace.client.gui.settings.SetAbstractItemButton;

public class ItemPopup extends CustomOverlay {

    private static final int            SCROLLBAR_WIDTH = 10;

    private final List<ItemStack>       options;
    private final SetAbstractItemButton itemButton;

    private Scrollbar                   scrollbar;
    private Container                   container;

    public ItemPopup(SetAbstractItemButton itemButton, List<ItemStack> options, BasicScreen bg) {

        super(bg);

        this.itemButton = itemButton;
        this.options = options;
    }

    @Override
    protected void createGui() {

        scrollbar = new ScrollbarVanilla(SCROLLBAR_WIDTH);
        container = new Container(scrollbar, 0, 0);

        Widget[] widgets = new Widget[options.size()];
        for (int i = 0; i < widgets.length; i++) {
            widgets[i] = new ItemButton(options.get(i), new ChooseItemButtonHandler());
        }

        container.addWidgets(widgets);
        containers.add(container);
        selectedContainer = container;
    }

    @Override
    protected void revalidateGui() {

        super.revalidateGui();
        List<Widget> widgets = container.getWidgets();

        int xButtons = width / ItemButton.WIDTH - 6;
        int yButtons = height / ItemButton.HEIGHT - 5;

        int lines = widgets.size() / xButtons;
        if (widgets.size() % xButtons != 0) {
            lines++;
        }
        yButtons = MathHelper.clamp_int(yButtons, 1, lines);

        int cWidth = xButtons * ItemButton.WIDTH + SCROLLBAR_WIDTH;
        int cHeight = yButtons * ItemButton.HEIGHT;
        int startX = (width - cWidth) / 2;
        int startY = (height - cHeight) / 2;

        int lineCount = 0;
        int x = startX;
        int y = startY;
        for (Widget w : widgets) {
            w.setPosition(x, y);
            lineCount++;
            x += ItemButton.HEIGHT;
            if (lineCount + 1 > xButtons) {
                lineCount = 0;
                x = startX;
                y += ItemButton.HEIGHT;
            }
        }

        scrollbar.setPosition(startX + cWidth - SCROLLBAR_WIDTH, startY);
        container.revalidate(startX, startY, cWidth, cHeight);
    }

    @Override
    public void drawBackground() {

        super.drawBackground();

        // Draw the 4 outlining rectangles (entire top, entire bottom, left (mid), right (mid) )
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        drawRect(container.left() - 1, container.top() - 1, container.right() - SCROLLBAR_WIDTH + 1, container.top(), 0xffffffff);
        drawRect(container.left() - 1, container.bottom(), container.right() - SCROLLBAR_WIDTH + 1, container.bottom() + 1, 0xffffffff);
        drawRect(container.left() - 1, container.top(), container.left(), container.bottom(), 0xffffffff);
        drawRect(container.right() - SCROLLBAR_WIDTH, container.top(), container.right() - SCROLLBAR_WIDTH + 1, container.bottom(), 0xffffffff);
        drawRect(container.left(), container.top(), container.right() - SCROLLBAR_WIDTH, container.bottom(), 0xff444444);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * "Hacky" close if user clicked outside of this container
     */
    @Override
    protected void mouseClicked(int mx, int my, int code) {

        if (container.inBounds(mx, my)) {
            container.mouseClicked(mx, my, MouseButton.fromCode(code));
        } else {
            close();
        }
    }

    private class ChooseItemButtonHandler extends LeftButtonHandler {

        @Override
        public void leftButtonClicked(Button button) {

            itemButton.setItem( ((ItemButton) button).getItem());
            close();
        }
    }

}
