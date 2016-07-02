
package de.unratedfilms.scriptspace.client.gui.settings;

import static de.unratedfilms.scriptspace.common.Consts.MOD_ID;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentTranslation;
import de.unratedfilms.guilib.core.Button;
import de.unratedfilms.guilib.core.Button.ButtonHandler;
import de.unratedfilms.guilib.core.Label;
import de.unratedfilms.guilib.core.Widget;
import de.unratedfilms.guilib.vanilla.ButtonVanilla;
import de.unratedfilms.scriptspace.client.gui.SimpleScrollableContainerScreen;
import de.unratedfilms.scriptspace.client.keys.KeyBindings;
import de.unratedfilms.scriptspace.client.selection.SelectionStorage;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBlock;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBoolean;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingFloat;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingInt;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingItemStack;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingString;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingStringList;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.ChangeProgramItemServerMessage;
import de.unratedfilms.scriptspace.net.messages.RunProgramServerMessage;

public class ConfigureProgramScreen extends SimpleScrollableContainerScreen {

    private static final int V_MARGIN = 10;
    private static final int H_MARGIN = 10;

    private final Program    program;
    private final int        programItemStackSlotId;

    private Label            titleLabel;
    private Button           applyAndRunButton;
    private Button           applyButton;
    private Button           cancelButton;

    public ConfigureProgramScreen(GuiScreen parent, Program program, int programItemStackSlotId) {

        super(parent, H_MARGIN, H_MARGIN, V_MARGIN + 20, V_MARGIN + 40);

        this.program = program;
        this.programItemStackSlotId = programItemStackSlotId;
    }

    @Override
    protected void createGui() {

        super.createGui();

        titleLabel = new Label(I18n.format("gui." + MOD_ID + ".configureProgram.title", program.getSourceScript().getName()));
        applyAndRunButton = new ButtonVanilla(120, 20, I18n.format("gui." + MOD_ID + ".configureProgram.applyAndRun"), new ApplyAndRunButtonHandler());
        applyButton = new ButtonVanilla(120, 20, I18n.format("gui." + MOD_ID + ".configureProgram.apply"), new ApplyButtonHandler());
        cancelButton = new ButtonVanilla(60, 20, I18n.format("gui." + MOD_ID + ".createProgram.cancel"), new CloseButtonHandler());
        mainContainer.addWidgets(titleLabel, applyAndRunButton, applyButton, cancelButton);

        for (Setting setting : program.getSettings()) {
            scrollableContainer.addWidgets(getSettingWidget(setting));
        }
    }

    @Override
    protected void revalidateGui() {

        super.revalidateGui();

        titleLabel.setPosition(width / 2, V_MARGIN);
        applyAndRunButton.setPosition(width / 2 - 154, height - V_MARGIN - 20);
        applyButton.setPosition(width / 2 - 30, height - V_MARGIN - 20);
        cancelButton.setPosition(width / 2 + 94, height - V_MARGIN - 20);
    }

    private Widget getSettingWidget(Setting setting) {

        if (setting instanceof SettingBoolean) {
            return new SetCheckbox((SettingBoolean) setting);
        } else if (setting instanceof SettingInt) {
            return new SetIntTextField((SettingInt) setting);
        } else if (setting instanceof SettingFloat) {
            return new SetFloatTextField((SettingFloat) setting);
        } else if (setting instanceof SettingString) {
            return new SetStringTextField((SettingString) setting);
        } else if (setting instanceof SettingStringList) {
            return new SetStringListButton((SettingStringList) setting);
        } else if (setting instanceof SettingBlock) {
            return new SetBlockButton((SettingBlock) setting);
        } else if (setting instanceof SettingItemStack) {
            return new SetItemStackButton((SettingItemStack) setting);
        } else {
            throw new IllegalArgumentException("Unknown setting type: " + setting.getClass().getName());
        }
    }

    private Program applySettings() {

        List<Setting> newSettings = new ArrayList<>();
        for (Widget widget : scrollableContainer.getWidgets()) {
            newSettings.add( ((SettingWidget) widget).applySetting());
        }

        return new Program(program.getSourceScript(), newSettings);
    }

    private class ApplyAndRunButtonHandler implements ButtonHandler {

        @Override
        public void buttonClicked(Button button) {

            // Construct the new program with the new settings and update the item in the player's hand
            Program newProgram = applySettings();
            NetworkService.DISPATCHER.sendToServer(new ChangeProgramItemServerMessage(programItemStackSlotId, newProgram));

            Selection chosenSelection = SelectionStorage.chosenSelection;
            if (chosenSelection == null) {
                // If no selection has been chosen yet, inform the player
                String chooseSelectionKey = GameSettings.getKeyDisplayString(KeyBindings.chooseSelection.getKeyCode());
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("message." + MOD_ID + ".noChosenSelectionError", chooseSelectionKey));
            } else {
                // Otherwise, run the new program on the chosen selection
                NetworkService.DISPATCHER.sendToServer(new RunProgramServerMessage(newProgram, chosenSelection));
            }

            close();
        }

    }

    private class ApplyButtonHandler implements ButtonHandler {

        @Override
        public void buttonClicked(Button button) {

            NetworkService.DISPATCHER.sendToServer(new ChangeProgramItemServerMessage(programItemStackSlotId, applySettings()));

            close();
        }

    }

}
