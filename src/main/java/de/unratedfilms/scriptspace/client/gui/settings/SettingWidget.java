
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.widgets.model.Container;
import de.unratedfilms.guilib.widgets.model.Label;
import de.unratedfilms.guilib.widgets.view.impl.ContainerAdjustingImpl;
import de.unratedfilms.guilib.widgets.view.impl.LabelImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;

public abstract class SettingWidget<S extends Setting> extends ContainerAdjustingImpl {

    protected final S         setting;

    private final Label       label;
    protected final Container settingContainer;

    public SettingWidget(S setting) {

        this.setting = setting;

        label = new LabelImpl(setting.displayName);
        settingContainer = new ContainerAdjustingImpl();
        addWidgets(label, settingContainer);

        // ----- Revalidation -----

        appendLayoutManager(c -> {
            settingContainer.setX(MC.fontRenderer.getStringWidth(label.getText()) + 10);
        });
    }

    public abstract S applySetting();

}
