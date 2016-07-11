
package de.unratedfilms.scriptspace.client.keys;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.ClientRegistry;
import de.unratedfilms.scriptspace.common.Consts;

public class KeyBindings {

    public static KeyBinding createProgram                   = createKeyBinding("createProgram", Keyboard.KEY_C);
    public static KeyBinding toggleChosenSelectionVisibility = createKeyBinding("toggleChosenSelectionVisibility", Keyboard.KEY_V);

    private static KeyBinding createKeyBinding(String name, int key) {

        return new KeyBinding("key." + Consts.MOD_ID + "." + name, key, "key.categories." + Consts.MOD_ID);
    }

    public static void initialize() {

        ClientRegistry.registerKeyBinding(createProgram);
        ClientRegistry.registerKeyBinding(toggleChosenSelectionVisibility);
    }

}
