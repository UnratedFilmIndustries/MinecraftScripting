
package de.unratedfilms.scriptspace.client.gui;

import org.lwjgl.input.Keyboard;
import de.unratedfilms.guilib.basic.BasicScreen;
import de.unratedfilms.guilib.basic.OverlayScreen;

public abstract class CustomOverlay extends OverlayScreen {

    public CustomOverlay(BasicScreen background) {

        super(background);
    }

    @Override
    public boolean doesGuiPauseGame() {

        return getParent().doesGuiPauseGame();
    }

    @Override
    protected void unhandledKeyTyped(char typedChar, int keyCode) {

        if (keyCode == Keyboard.KEY_ESCAPE || typedChar == '\r') {
            close();
        }
    }

}
