
package de.unratedfilms.scriptspace.client.keys;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import de.unratedfilms.scriptspace.client.selection.SelectionStorage;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.AvailableScriptsRequestServerMessage;

public class KeyHandler {

    // We catch keyboard events AND mouse events in case the key has been set to a mouse button
    @SubscribeEvent
    public void onKeyInput(InputEvent event) {

        if (KeyBindings.createProgram.isPressed()) {
            onCreateProgram();
        } else if (KeyBindings.toggleChosenSelectionVisibility.isPressed()) {
            onToggleChosenSelectionVisibility();
        }
    }

    private void onCreateProgram() {

        // Request the available scripts which are located on the server; the incoming response will automatically open up a create program GUI
        NetworkService.DISPATCHER.sendToServer(new AvailableScriptsRequestServerMessage());
    }

    private void onToggleChosenSelectionVisibility() {

        SelectionStorage.renderChosenSelection = !SelectionStorage.renderChosenSelection;
    }

}
