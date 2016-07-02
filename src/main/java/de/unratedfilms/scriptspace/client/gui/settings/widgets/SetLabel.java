
package de.unratedfilms.scriptspace.client.gui.settings.widgets;

import de.unratedfilms.guilib.core.Scrollbar.Shiftable;
import de.unratedfilms.guilib.focusable.FocusableWidget;

public class SetLabel extends FocusableWidget implements Shiftable {

    private final String str;
    private boolean      focused;

    public SetLabel(String text, int width) {

        super(width, 11);

        str = text;
    }

    @Override
    public void draw(int mx, int my) {

        boolean hover = inBounds(mx, my) || focused;
        if (hover) {
            drawRect(x, y, x + width, y + height, 0x99999999);
        }
        MC.fontRenderer.drawStringWithShadow(str, x + 4, y + 2, hover ? 16777120 : 0xffffff);
    }

    @Override
    public boolean click(int mx, int my) {

        return inBounds(mx, my);
    }

    public String getText() {

        return str;
    }

    @Override
    public void shiftY(int dy) {

        y += dy;
    }

    @Override
    public void focusGained() {

        focused = true;
    }

    @Override
    public void focusLost() {

        focused = false;
    }

}
