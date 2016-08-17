
package de.unratedfilms.scriptspace.client.gui;

import static de.unratedfilms.scriptspace.common.Consts.MOD_ID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import de.unratedfilms.guilib.core.Button;
import de.unratedfilms.guilib.core.Button.LeftButtonHandler;
import de.unratedfilms.guilib.core.Label;
import de.unratedfilms.guilib.focusable.FocusableLabel;
import de.unratedfilms.guilib.vanilla.ButtonVanilla;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.ScriptCompilationException;
import de.unratedfilms.scriptspace.common.script.SourceScript;
import de.unratedfilms.scriptspace.common.script.services.ScriptCompilationService;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.ChangeProgramItemServerMessage;

public class CreateProgramScreen extends SimpleScrollableContainerScreen {

    private static final int         V_MARGIN = 10;
    private static final int         H_MARGIN = 10;

    private final List<SourceScript> scripts;

    private Label                    titleLabel;
    private Button                   finishButton;
    private Button                   cancelButton;

    public CreateProgramScreen(GuiScreen parent, Set<SourceScript> scripts) {

        super(parent, H_MARGIN, H_MARGIN, V_MARGIN + 20, V_MARGIN + 40);

        this.scripts = new ArrayList<>(scripts);
        Collections.sort(this.scripts);
    }

    @Override
    protected void createGui() {

        super.createGui();

        titleLabel = new Label(I18n.format("gui." + MOD_ID + ".createProgram.title"));
        finishButton = new ButtonVanilla(148, 20, I18n.format("gui." + MOD_ID + ".createProgram.finish"), new FinishButtonHandler());
        cancelButton = new ButtonVanilla(148, 20, I18n.format("gui." + MOD_ID + ".createProgram.cancel"), new CloseButtonHandler());
        mainContainer.addWidgets(titleLabel, finishButton, cancelButton);

        for (SourceScript script : scripts) {
            FocusableLabel scriptLabel = new FocusableLabel(script.getName(), false);
            scriptLabel.setUserData(script);
            scrollableContainer.addWidgets(scriptLabel);
        }
    }

    @Override
    protected void revalidateGui() {

        super.revalidateGui();

        titleLabel.setPosition(width / 2, V_MARGIN);
        finishButton.setPosition(width / 2 - 150, height - V_MARGIN - 20);
        cancelButton.setPosition(width / 2 + 2, height - V_MARGIN - 20);
    }

    private class FinishButtonHandler extends LeftButtonHandler {

        @Override
        public void leftButtonClicked(Button button) {

            FocusableLabel focusedScriptLabel = (FocusableLabel) scrollableContainer.getFocusedWidget();

            if (focusedScriptLabel != null) {
                SourceScript selectedScript = (SourceScript) focusedScriptLabel.getUserData();

                try {
                    Program program = new Program(selectedScript);

                    // Adds a new item stack with the given program to the players inventory
                    NetworkService.DISPATCHER.sendToServer(new ChangeProgramItemServerMessage(-1, program));
                } catch (ScriptCompilationException e) {
                    ScriptCompilationService.sendErrorMessagesOnCompilationException(e, Minecraft.getMinecraft().thePlayer);
                } finally {
                    close();
                }
            }
        }

    }

}
