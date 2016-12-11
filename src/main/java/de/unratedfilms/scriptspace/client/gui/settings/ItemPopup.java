
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.List;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.integration.BasicScreen;
import de.unratedfilms.guilib.integration.OverlayScreen;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.guilib.widgets.model.Button.LeftButtonHandler;
import de.unratedfilms.guilib.widgets.model.ButtonItem;
import de.unratedfilms.guilib.widgets.model.ContainerFlexible;
import de.unratedfilms.guilib.widgets.model.Scrollbar;
import de.unratedfilms.guilib.widgets.view.impl.ButtonItemImpl;
import de.unratedfilms.guilib.widgets.view.impl.ContainerClippingImpl;
import de.unratedfilms.guilib.widgets.view.impl.ContainerScrollableImpl;
import de.unratedfilms.guilib.widgets.view.impl.ScrollbarImpl;

public class ItemPopup extends OverlayScreen {

    private final List<ItemStack>                    options;
    private final SettingWidgetAbstractItemButton<?> itemButton;

    private ContainerFlexible                        mainContainer;

    private ContainerFlexible                        scrollableContainer;
    private Scrollbar                                scrollbar;

    public ItemPopup(SettingWidgetAbstractItemButton<?> itemButton, List<ItemStack> options, BasicScreen bg) {

        super(bg);

        this.itemButton = itemButton;
        this.options = options;
    }

    @Override
    protected void createGui() {

        mainContainer = new ContainerClippingImpl();
        setRootWidget(mainContainer);

        scrollbar = new ScrollbarImpl(0);
        scrollableContainer = new ContainerScrollableImpl(scrollbar, 0);
        mainContainer.addWidgets(scrollableContainer);

        ButtonItem[] buttons = new ButtonItem[options.size()];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new ButtonItemImpl(options.get(i), new ChooseItemButtonHandler());
        }
        scrollableContainer.addWidgets(buttons);

        // ----- Revalidation -----

        mainContainer.appendLayoutManager(c -> {
            int xButtons = mainContainer.getWidth() / ButtonItemImpl.SIZE - 6;
            int yButtons = mainContainer.getHeight() / ButtonItemImpl.SIZE - 5;

            int lines = buttons.length / xButtons;
            if (buttons.length % xButtons != 0) {
                lines++;
            }
            yButtons = MathHelper.clamp_int(yButtons, 1, lines);

            int cWidth = xButtons * ButtonItemImpl.SIZE + scrollbar.getWidth();
            int cHeight = yButtons * ButtonItemImpl.SIZE;
            int startX = (mainContainer.getWidth() - cWidth) / 2;
            int startY = (mainContainer.getHeight() - cHeight) / 2;

            scrollableContainer.setBounds(startX, startY, cWidth, cHeight);
        });

        scrollableContainer.appendLayoutManager(c -> {
            scrollbar.setPosition(scrollableContainer.getWidth() - scrollbar.getWidth(), 0);

            int x = 0;
            int y = 0;
            for (ButtonItem button : buttons) {
                button.setPosition(x, y);
                x += ButtonItemImpl.SIZE;
                if (x + ButtonItemImpl.SIZE > scrollableContainer.getWidth()) {
                    x = 0;
                    y += ButtonItemImpl.SIZE;
                }
            }
        });
    }

    @Override
    public void drawBackground() {

        super.drawBackground();

        int cLeft = scrollableContainer.getX();
        int cRight = scrollableContainer.getX() + scrollableContainer.getWidth();
        int cTop = scrollableContainer.getY();
        int cBottom = scrollableContainer.getY() + scrollableContainer.getHeight();

        // Draw the 4 outlining rectangles (entire top, entire bottom, left (mid), right (mid) )
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        drawRect(cLeft - 1, cTop - 1, cRight - scrollbar.getWidth() + 1, cTop, 0xffffffff);
        drawRect(cLeft - 1, cBottom, cRight - scrollbar.getWidth() + 1, cBottom + 1, 0xffffffff);
        drawRect(cLeft - 1, cTop, cLeft, cBottom, 0xffffffff);
        drawRect(cRight - scrollbar.getWidth(), cTop, cRight - scrollbar.getWidth() + 1, cBottom, 0xffffffff);
        drawRect(cLeft, cTop, cRight - scrollbar.getWidth(), cBottom, 0xff444444);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * "Hacky" close if user clicked outside of this container
     */
    @Override
    protected void mouseClicked(int mx, int my, int code) {

        if (scrollableContainer.inGlobalBounds(getRootViewport(), mx, my)) {
            scrollableContainer.mousePressed(getRootViewport(), mx, my, MouseButton.fromCode(code));
        } else {
            close();
        }
    }

    private class ChooseItemButtonHandler extends LeftButtonHandler {

        @Override
        public void leftButtonClicked(Button button) {

            itemButton.button.setItemStack( ((ButtonItem) button).getItemStack());
            close();
        }
    }

}
