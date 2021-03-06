
package de.unratedfilms.scriptspace.client.gui.settings;

import static de.unratedfilms.scriptspace.common.Consts.MOD_ID;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.widgets.model.Button.FilteredButtonHandler;
import de.unratedfilms.guilib.widgets.model.ButtonLabel;
import de.unratedfilms.guilib.widgets.model.Label;
import de.unratedfilms.guilib.widgets.view.impl.ButtonLabelImpl;
import de.unratedfilms.guilib.widgets.view.impl.LabelImpl;
import de.unratedfilms.scriptspace.client.gui.SimpleScrollableContainerScreen;
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

    private static final int             V_MARGIN                = 10;
    private static final int             H_MARGIN                = 10;

    private final Program                program;
    private final int                    programItemStackSlotId;

    private Label                        titleLabel;
    private ButtonLabel                  applyAndRunButton;
    private ButtonLabel                  applyButton;
    private ButtonLabel                  cancelButton;

    private SettingWidgetStringTextField programTitleSetting;                         // The title setting each program has
    private final List<SettingWidget<?>> programSpecificSettings = new ArrayList<>(); // The other settings which are defined by the program itself

    public ConfigureProgramScreen(GuiScreen parent, Program program, int programItemStackSlotId) {

        super(parent, H_MARGIN, H_MARGIN, V_MARGIN + 20, V_MARGIN + 40);

        this.program = program;
        this.programItemStackSlotId = programItemStackSlotId;
    }

    @Override
    protected void createGui() {

        super.createGui();

        titleLabel = new LabelImpl(I18n.format("gui." + MOD_ID + ".configureProgram.title", program.getSourceScript().getName()));
        applyAndRunButton = new ButtonLabelImpl(I18n.format("gui." + MOD_ID + ".configureProgram.applyAndRun"), new FilteredButtonHandler(MouseButton.LEFT, (b, mb) -> {
            runProgram(applySettings());
            close();
        }));
        applyButton = new ButtonLabelImpl(I18n.format("gui." + MOD_ID + ".configureProgram.apply"), new FilteredButtonHandler(MouseButton.LEFT, (b, mb) -> {
            applySettings();
            close();
        }));
        cancelButton = new ButtonLabelImpl(I18n.format("gui." + MOD_ID + ".createProgram.cancel"), new FilteredButtonHandler(MouseButton.LEFT, (b, mb) -> close()));
        mainContainer.addWidgets(titleLabel, applyAndRunButton, applyButton, cancelButton);

        // Add a widget which the user can use to change the program's title; it's a "virtual setting" since it's only created for this GUI
        // Afterwards, the title is stored in a normal String variable (Program.title)
        programTitleSetting = new SettingWidgetStringTextField(new SettingString("title", I18n.format("gui." + MOD_ID + ".configureProgram.programTitle"), program.getTitle()));
        scrollableContainer.addWidgets(programTitleSetting);

        // Add a widget for each setting
        for (Setting setting : program.getSettings()) {
            SettingWidget<?> settingWidget = getSettingWidget(setting);
            scrollableContainer.addWidgets(settingWidget);
            programSpecificSettings.add(settingWidget);
        }

        // ----- Revalidation -----

        mainContainer.appendLayoutManager(c -> {
            titleLabel.setPosition( (mainContainer.getWidth() - titleLabel.getWidth()) / 2, V_MARGIN);
            applyAndRunButton.setBounds(mainContainer.getWidth() / 2 - 154, mainContainer.getHeight() - V_MARGIN - 20, 120, 20);
            applyButton.setBounds(mainContainer.getWidth() / 2 - 30, mainContainer.getHeight() - V_MARGIN - 20, 120, 20);
            cancelButton.setBounds(mainContainer.getWidth() / 2 + 94, mainContainer.getHeight() - V_MARGIN - 20, 60, 20);
        });
    }

    private SettingWidget<?> getSettingWidget(Setting setting) {

        if (setting instanceof SettingBoolean) {
            return new SettingWidgetCheckbox((SettingBoolean) setting);
        } else if (setting instanceof SettingInt) {
            return new SettingWidgetIntTextField((SettingInt) setting);
        } else if (setting instanceof SettingFloat) {
            return new SettingWidgetFloatTextField((SettingFloat) setting);
        } else if (setting instanceof SettingString) {
            return new SettingWidgetStringTextField((SettingString) setting);
        } else if (setting instanceof SettingStringList) {
            return new SettingWidgetStringListButton((SettingStringList) setting);
        } else if (setting instanceof SettingBlock) {
            return new SettingWidgetBlockButton((SettingBlock) setting);
        } else if (setting instanceof SettingItemStack) {
            return new SettingWidgetItemStackButton((SettingItemStack) setting);
        } else {
            throw new IllegalArgumentException("Unknown setting type: " + setting.getClass().getName());
        }
    }

    private Program applySettings() {

        String title = programTitleSetting.applySetting().string;

        List<Setting> newSettings = new ArrayList<>();
        for (SettingWidget<?> widget : programSpecificSettings) {
            newSettings.add(widget.applySetting());
        }

        Program newProgram = new Program(title, program.getSourceScript(), newSettings);

        // Update the item in the player's hand
        NetworkService.DISPATCHER.sendToServer(new ChangeProgramItemServerMessage(programItemStackSlotId, newProgram));

        return newProgram;
    }

    private void runProgram(Program program) {

        Selection chosenSelection = SelectionStorage.chosenSelection;
        if (chosenSelection == null) {
            // If no selection has been chosen yet, inform the player
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("message." + MOD_ID + ".noChosenSelectionError"));
        } else {
            // Otherwise, run the new program on the chosen selection
            NetworkService.DISPATCHER.sendToServer(new RunProgramServerMessage(program, chosenSelection));
        }
    }

}
