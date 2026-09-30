package org.bfg.gui.tabs.file.property.controls;

import javax.swing.*;
import java.util.Objects;

/**
 * A {@link IValueControl} component based on a {@link JComboBox} containing {@link String} objects. Supports a callback
 * when the value changes.
 */
public final class StringComboBoxControl extends JComboBox<String> implements IValueControl {

    /**
     * Creates the control with the given options.
     *
     * @param values                The values that can be selected, must not be {@link null}.
     * @param controlUpdateCallback The callback when the selected value changes, must not be {@link null}.
     */
    public StringComboBoxControl(String[] values, IControlUpdateCallback controlUpdateCallback) {
        super(values);
        Objects.requireNonNull(values);
        Objects.requireNonNull(controlUpdateCallback);
        addActionListener(actionEvent -> controlUpdateCallback.onUpdate(this));
    }

    @Override
    public Object getControlValue() {
        return getSelectedItem();
    }

    /**
     * Returns the controls value as an {@link String}.
     *
     * @return The {@link String} value of this control.
     */
    public String getStringValue() {
        return (String) getControlValue();
    }
}
