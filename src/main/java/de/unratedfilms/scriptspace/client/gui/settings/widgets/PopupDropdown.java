
package de.unratedfilms.scriptspace.client.gui.settings.widgets;

import de.unratedfilms.guilib.basic.BasicScreen;
import de.unratedfilms.guilib.core.Container;
import de.unratedfilms.guilib.core.Scrollbar;
import de.unratedfilms.guilib.focusable.FocusableWidget;
import de.unratedfilms.guilib.vanilla.ScrollbarVanilla;
import de.unratedfilms.scriptspace.client.gui.CustomOverlay;
import de.unratedfilms.scriptspace.client.gui.settings.SetStringListButton;

/**
 * A hack-ish way to implement a nice looking drop-down menu with GuiLib.
 *
 */
public class PopupDropdown extends CustomOverlay {

    private final SetStringListButton button;
    private final String[]            options;

    private Scrollbar                 scrollbar;
    private Container                 container;
    private int                       x, y;
    private int                       cWidth, cHeight;

    public PopupDropdown(SetStringListButton button, String[] options, BasicScreen bg) {

        super(bg);

        this.button = button;
        this.options = options;
    }

    @Override
    protected void createGui() {

        scrollbar = new ScrollbarVanilla(7);
        container = new Container(scrollbar, 0, 0);
        for (String s : options) {
            int width = mc.fontRenderer.getStringWidth(s);
            if (width > cWidth) {
                cWidth = width;
            }
        }
        cWidth += 15; // 4 gap on each side, 7 for the scrollbar
        cHeight = options.length * 11;

        FocusableWidget[] widgets = new FocusableWidget[options.length];
        for (int i = 0; i < widgets.length; i++) {
            widgets[i] = new SetLabel(options[i], cWidth - 7);
        }

        container.addWidgets(widgets);
        containers.add(container);
        selectedContainer = container;
    }

    /**
     * "Hacky" close if user clicked outside of this container
     */
    @Override
    protected void mouseClicked(int mx, int my, int code) {

        if (code == 0) {
            if (container.inBounds(mx, my)) {
                container.mouseClicked(mx, my);
            } else {
                close();
            }
        }
    }

    /**
     * "Hacky" close if user selected an option
     */
    @Override
    public void updateScreen() {

        super.updateScreen();
        if (container.hasFocusedWidget()) {
            button.setSelectedText( ((SetLabel) container.getFocusedWidget()).getText());
            close();
        }
    }

    @Override
    protected void revalidateGui() {

        super.revalidateGui();
        x = button.getX() + button.getWidth() / 2;
        y = button.getY() + button.getHeight() / 2;
        // Don't let it go off of the screen... do some math
        if (x + cWidth > width) {
            x = width - cWidth - 10;
        }
        if (y + cHeight > height) {
            cHeight = height - y - 10;
        }

        int widgetY = y;
        for (FocusableWidget fw : container.getFocusableWidgets()) {
            fw.setPosition(x, widgetY);
            widgetY += 11;
        }

        scrollbar.setPosition(x + cWidth - 7, y);
        container.revalidate(x, y, cWidth, cHeight);
    }

    @Override
    public void drawBackground() {

        super.drawBackground();
        // Draw the 4 outlining rectangles (entire top, entire bottom, left (mid), right (mid) )
        drawRect(container.left() - 1, container.top() - 1, container.right() - 6, container.top(), 0xffffffff);
        drawRect(container.left() - 1, container.bottom(), container.right() - 6, container.bottom() + 1, 0xffffffff);
        drawRect(container.left() - 1, container.top(), container.left(), container.bottom(), 0xffffffff);
        drawRect(container.right() - 7, container.top(), container.right() - 6, container.bottom(), 0xffffffff);

        drawRect(container.left(), container.top(), container.right() - 7, container.bottom(), 0xff444444);
    }

}
