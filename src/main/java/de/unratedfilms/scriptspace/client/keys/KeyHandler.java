
package de.unratedfilms.scriptspace.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import de.unratedfilms.scriptspace.client.selection.SelectionStorage;
import de.unratedfilms.scriptspace.common.items.CustomItems;
import de.unratedfilms.scriptspace.common.items.ItemSelection;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.AvailableScriptsRequestServerMessage;

public class KeyHandler {

    // We catch keyboard events AND mouse events in case the key has been set to a mouse button
    @SubscribeEvent
    public void onKeyInput(InputEvent event) {

        if (KeyBindings.createProgram.isPressed()) {
            onCreateProgram();
        } else if (KeyBindings.chooseSelection.isPressed()) {
            onChooseSelection();
        }
    }

    private void onCreateProgram() {

        // Request the available scripts which are located on the server; the incoming response will automatically open up a create program GUI
        NetworkService.DISPATCHER.sendToServer(new AvailableScriptsRequestServerMessage());
    }

    private void onChooseSelection() {

        // If there's a currently held selection, choose it; otherwise, choose null
        ItemStack heldStack = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
        if (heldStack != null && heldStack.getItem() == CustomItems.SELECTION) {
            Selection heldSelection = ItemSelection.getSelection(heldStack);
            SelectionStorage.chosenSelection = heldSelection;
        } else {
            SelectionStorage.chosenSelection = null;
        }
    }

}
