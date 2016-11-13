
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.Locale;
import org.apache.commons.lang3.math.NumberUtils;
import de.unratedfilms.guilib.widgets.model.TextField;
import de.unratedfilms.guilib.widgets.model.TextField.DecimalNumberFilter;
import de.unratedfilms.guilib.widgets.view.impl.TextFieldImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingFloat;

public class SettingWidgetFloatTextField extends SettingWidget<SettingFloat> {

    private final TextField textField;

    public SettingWidgetFloatTextField(SettingFloat setting) {

        super(setting);

        textField = new TextFieldImpl(new DecimalNumberFilter());
        textField.setMaxLength(15);
        textField.setText(String.format(Locale.ENGLISH, "%.3f", setting.value));
        settingContainer.addWidgets(textField);

        // ----- Revalidation -----

        settingContainer.appendLayoutManager(() -> {
            textField.setSize(60, 14);
        });
    }

    @Override
    public SettingFloat applySetting() {

        float value = NumberUtils.toFloat(textField.getText(), setting.value);
        return setting.withValue(value);
    }

}
