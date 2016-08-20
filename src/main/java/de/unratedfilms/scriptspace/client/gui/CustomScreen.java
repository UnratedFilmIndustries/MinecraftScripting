
package de.unratedfilms.scriptspace.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import de.unratedfilms.guilib.integration.BasicScreen;

public abstract class CustomScreen extends BasicScreen {

    public CustomScreen(GuiScreen parent) {

        super(parent);
    }

    @Override
    protected void unhandledKeyTyped(char typedChar, int keyCode) {

        if (keyCode == Keyboard.KEY_ESCAPE) {
            close();
        }
    }

}
