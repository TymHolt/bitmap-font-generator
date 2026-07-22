package org.bfg.gui.controls;

import javax.swing.*;

final class BooleanCheckBoxControl extends Control implements IBooleanValue {

    private final JCheckBox checkBox;
    private boolean currentValue;

    BooleanCheckBoxControl(Runnable onAction) {
        super(onAction);
        this.checkBox = new JCheckBox();
        this.checkBox.addChangeListener(changeEvent -> this.onAction.run());
        this.currentValue = false;
    }

    @Override
    JComponent getComponent() {
        return this.checkBox;
    }

    @Override
    void readComponentValue() {
        final boolean newValue = this.checkBox.isSelected();
        setHasChanged(newValue != this.currentValue);
        this.currentValue = newValue;
    }

    @Override
    public boolean getValue() {
        return this.currentValue;
    }
}
