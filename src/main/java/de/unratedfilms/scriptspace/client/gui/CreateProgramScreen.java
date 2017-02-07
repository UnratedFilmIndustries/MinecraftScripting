
package de.unratedfilms.scriptspace.client.gui;

import static de.unratedfilms.scriptspace.common.Consts.MOD_ID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.widgets.model.Button.FilteredButtonHandler;
import de.unratedfilms.guilib.widgets.model.ButtonLabel;
import de.unratedfilms.guilib.widgets.model.Label;
import de.unratedfilms.guilib.widgets.view.impl.ButtonLabelImpl;
import de.unratedfilms.guilib.widgets.view.impl.LabelFocusableImpl;
import de.unratedfilms.guilib.widgets.view.impl.LabelImpl;
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
    private ButtonLabel              cancelButton;

    public CreateProgramScreen(GuiScreen parent, Set<SourceScript> scripts) {

        super(parent, H_MARGIN, H_MARGIN, V_MARGIN + 20, V_MARGIN + 40);

        this.scripts = new ArrayList<>(scripts);
        Collections.sort(this.scripts);
    }

    @Override
    protected void createGui() {

        super.createGui();

        titleLabel = new LabelImpl(I18n.format("gui." + MOD_ID + ".createProgram.title"));
        cancelButton = new ButtonLabelImpl(I18n.format("gui." + MOD_ID + ".createProgram.cancel"), new FilteredButtonHandler(MouseButton.LEFT, (b, mb) -> close()));
        mainContainer.addWidgets(titleLabel, cancelButton);

        for (SourceScript script : scripts) {
            scrollableContainer.addWidgets(new CreateProgramFocusableLabel(script));
        }

        // ----- Revalidation -----

        mainContainer.appendLayoutManager(c -> {
            titleLabel.setPosition( (mainContainer.getWidth() - titleLabel.getWidth()) / 2, V_MARGIN);
            cancelButton.setBounds(mainContainer.getWidth() / 2 - 100, mainContainer.getHeight() - V_MARGIN - 20, 200, 20);
        });
    }

    private class CreateProgramFocusableLabel extends LabelFocusableImpl {

        private CreateProgramFocusableLabel(SourceScript script) {

            super(script.getName());

            setUserData(script);
        }

        @Override
        public void focusGained() {

            try {
                Program program = new Program((SourceScript) getUserData());

                // Adds a new item stack with the given program to the players inventory
                NetworkService.DISPATCHER.sendToServer(new ChangeProgramItemServerMessage(-1, program));
            } catch (ScriptCompilationException e) {
                ScriptCompilationService.sendErrorMessagesOnCompilationException(e, Minecraft.getMinecraft().player);
            } finally {
                close();
            }
        }

    }

}
