
package de.unratedfilms.scriptspace.client.gui.settings;

import org.apache.commons.lang3.math.NumberUtils;
import de.unratedfilms.guilib.widgets.model.TextField;
import de.unratedfilms.guilib.widgets.model.TextField.IntegerNumberFilter;
import de.unratedfilms.guilib.widgets.view.impl.TextFieldImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingInt;

public class SettingWidgetIntTextField extends SettingWidget<SettingInt> {

    private final TextField textField;

    public SettingWidgetIntTextField(SettingInt setting) {

        super(setting);

        textField = new TextFieldImpl(new IntegerNumberFilter());
        textField.setMaxLength(15);
        textField.setText(String.valueOf(setting.value));
        settingContainer.addWidgets(textField);

        // ----- Revalidation -----

        settingContainer.appendLayoutManager(() -> {
            textField.setSize(60, 14);
        });
    }

    @Override
    public SettingInt applySetting() {

        int value = NumberUtils.toInt(textField.getText(), setting.value);
        return setting.withValue(value);
    }

}
