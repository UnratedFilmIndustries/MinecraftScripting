
package de.unratedfilms.scriptspace.client.gui;

import org.lwjgl.input.Keyboard;
import de.unratedfilms.guilib.integration.BasicScreen;
import de.unratedfilms.guilib.integration.OverlayScreen;

public abstract class CustomOverlay extends OverlayScreen {

    public CustomOverlay(BasicScreen background) {

        super(background);
    }

    @Override
    protected void unhandledKeyTyped(char typedChar, int keyCode) {

        if (keyCode == Keyboard.KEY_ESCAPE || typedChar == '\r') {
            close();
        }
    }

}
