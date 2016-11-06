
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.ArrayList;
import java.util.List;
import de.unratedfilms.guilib.widgets.model.Dropdown.GenericOption;
import de.unratedfilms.guilib.widgets.view.impl.DropdownLabelImpl;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingStringList;

public class SettingWidgetStringListButton extends SettingWidget<SettingStringList> {

    private final DropdownLabelImpl<GenericOption<String>> dropdown;

    public SettingWidgetStringListButton(SettingStringList setting) {

        super(setting);

        List<GenericOption<String>> options = new ArrayList<>();
        for (String optionString : setting.options) {
            options.add(new GenericOption<>(optionString));
        }
        dropdown = new DropdownLabelImpl<>(options);
        dropdown.setSelectedOption(new GenericOption<>(setting.selected));
        settingContainer.addWidget(dropdown);

        // ----- Revalidation -----

        settingContainer.appendLayoutManager(() -> {
            // The width of the dropdown button should always be equal to the width of the dropdown menu
            dropdown.setSize(dropdown.getExtWidth(), 14);
        });
    }

    @Override
    public SettingStringList applySetting() {

        return setting.withValue(dropdown.getSelectedOption().getDisplayObject());
    }

}
