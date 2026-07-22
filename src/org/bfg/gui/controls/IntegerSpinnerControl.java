package org.bfg.gui.controls;

import javax.swing.*;

final class IntegerSpinnerControl extends Control implements IIntegerValue {

    private final JSpinner spinner;
    private int currentValue;

    IntegerSpinnerControl(Runnable onAction, int value, int min, int max, int stepSize) {
        super(onAction);
        this.spinner = new JSpinner(new SpinnerNumberModel(value, min, max, stepSize));
        this.spinner.addChangeListener(changeEvent -> this.onAction.run());
        this.currentValue = value;
    }

    @Override
    JComponent getComponent() {
        return this.spinner;
    }

    @Override
    void readComponentValue() {
        final int newValue = (int) this.spinner.getValue();
        setHasChanged(newValue != this.currentValue);
        this.currentValue = newValue;
    }

    @Override
    public int getValue() {
        return this.currentValue;
    }
}
