package org.bfg.gui.controls;

import javax.swing.*;

final class StringComboBoxControl extends Control implements IStringValue {

    private final JComboBox<String> comboBox;
    private String currentValue;

    StringComboBoxControl(Runnable onAction, String[] values) {
        super(onAction);
        this.comboBox = new JComboBox<>(values);
        this.comboBox.addActionListener(actionEvent -> this.onAction.run());
        this.currentValue = "";
    }

    @Override
    JComponent getComponent() {
        return this.comboBox;
    }

    @Override
    void readComponentValue() {
        final String newValue = (String) this.comboBox.getSelectedItem();
        if (newValue == null)
            return;

        setHasChanged(!newValue.equals(this.currentValue));
        this.currentValue = newValue;
    }

    @Override
    public String getValue() {
        return this.currentValue;
    }
}
