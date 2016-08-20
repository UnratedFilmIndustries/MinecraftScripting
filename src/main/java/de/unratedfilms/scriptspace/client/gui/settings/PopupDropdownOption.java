
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.core.FocusableWidget;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.core.WidgetAdapter;

public class PopupDropdownOption extends WidgetAdapter implements FocusableWidget {

    private final String text;
    private boolean      focused;

    public PopupDropdownOption(String text, int width) {

        super(width, 11);

        this.text = text;
    }

    public String getText() {

        return text;
    }

    @Override
    public boolean isFocused() {

        return focused;
    }

    @Override
    public void draw(int mx, int my) {

        boolean hover = inBounds(mx, my) || focused;
        if (hover) {
            drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x99999999);
        }
        MC.fontRenderer.drawStringWithShadow(text, getX() + 4, getY() + 2, hover ? 16777120 : 0xffffff);
    }

    @Override
    public boolean mousePressed(int mx, int my, MouseButton mouseButton) {

        return mouseButton == MouseButton.LEFT && inBounds(mx, my);
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
