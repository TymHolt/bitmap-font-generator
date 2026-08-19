package org.bfg.gui.tabs.file.property.controls;

import javax.swing.*;
import java.util.Objects;

public final class StringComboBoxControl extends JComboBox<String> implements IValueControl {

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

    public String getStringValue() {
        return (String) getControlValue();
    }
}
