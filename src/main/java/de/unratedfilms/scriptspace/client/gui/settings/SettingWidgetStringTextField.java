
package de.unratedfilms.scriptspace.client.gui.settings;

import de.unratedfilms.guilib.widgets.model.TextField;
import de.unratedfilms.guilib.widgets.model.TextField.VanillaFilter;
import de.unratedfilms.guilib.widgets.view.impl.TextFieldImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingString;

public class SettingWidgetStringTextField extends SettingWidget<SettingString> {

    private final TextField textField;

    public SettingWidgetStringTextField(SettingString setting) {

        super(setting);

        textField = new TextFieldImpl(new VanillaFilter());
        textField.setMaxLength(100);
        textField.setText(setting.string);
        settingContainer.addWidget(textField);

        // ----- Revalidation -----

        settingContainer.appendLayoutManager(() -> {
            textField.setHeight(14);
        });
    }

    @Override
    public SettingString applySetting() {

        return setting.withValue(textField.getText());
    }

}
