
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.List;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.core.Widget;
import de.unratedfilms.guilib.integration.BasicScreen;
import de.unratedfilms.guilib.integration.Container;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.guilib.widgets.model.Button.LeftButtonHandler;
import de.unratedfilms.guilib.widgets.model.ButtonItem;
import de.unratedfilms.guilib.widgets.model.Scrollbar;
import de.unratedfilms.guilib.widgets.view.impl.ButtonItemImpl;
import de.unratedfilms.guilib.widgets.view.impl.ScrollbarImpl;
import de.unratedfilms.scriptspace.client.gui.CustomOverlay;

public class ItemPopup extends CustomOverlay {

    private static final int                      SCROLLBAR_WIDTH = 10;

    private final List<ItemStack>                 options;
    private final SettingWidgetAbstractItemButton itemButton;

    private Scrollbar                             scrollbar;
    private Container                             container;

    public ItemPopup(SettingWidgetAbstractItemButton itemButton, List<ItemStack> options, BasicScreen bg) {

        super(bg);

        this.itemButton = itemButton;
        this.options = options;
    }

    @Override
    protected void createGui() {

        scrollbar = new ScrollbarImpl(SCROLLBAR_WIDTH);
        container = new Container(scrollbar, 0, 0);

        Widget[] widgets = new Widget[options.size()];
        for (int i = 0; i < widgets.length; i++) {
            widgets[i] = new ButtonItemImpl(options.get(i), new ChooseItemButtonHandler());
        }

        container.addWidgets(widgets);
        containers.add(container);
        selectedContainer = container;
    }

    @Override
    protected void revalidateGui() {

        super.revalidateGui();
        List<Widget> widgets = container.getWidgets();

        int xButtons = width / ButtonItemImpl.WIDTH - 6;
        int yButtons = height / ButtonItemImpl.HEIGHT - 5;

        int lines = widgets.size() / xButtons;
        if (widgets.size() % xButtons != 0) {
            lines++;
        }
        yButtons = MathHelper.clamp_int(yButtons, 1, lines);

        int cWidth = xButtons * ButtonItemImpl.WIDTH + SCROLLBAR_WIDTH;
        int cHeight = yButtons * ButtonItemImpl.HEIGHT;
        int startX = (width - cWidth) / 2;
        int startY = (height - cHeight) / 2;

        int lineCount = 0;
        int x = startX;
        int y = startY;
        for (Widget w : widgets) {
            w.setPosition(x, y);
            lineCount++;
            x += ButtonItemImpl.HEIGHT;
            if (lineCount + 1 > xButtons) {
                lineCount = 0;
                x = startX;
                y += ButtonItemImpl.HEIGHT;
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
            container.mousePressed(mx, my, MouseButton.fromCode(code));
        } else {
            close();
        }
    }

    private class ChooseItemButtonHandler extends LeftButtonHandler {

        @Override
        public void leftButtonClicked(Button button) {

            itemButton.setItemStack( ((ButtonItem) button).getItemStack());
            close();
        }
    }

}
