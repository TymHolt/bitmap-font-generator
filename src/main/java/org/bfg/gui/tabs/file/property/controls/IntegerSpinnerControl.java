package org.bfg.gui.tabs.file.property.controls;

import javax.swing.*;
import java.util.Objects;

public final class IntegerSpinnerControl extends JSpinner implements IValueControl {

    public IntegerSpinnerControl(int value, int min, int max, int step, IControlUpdateCallback controlUpdateCallback) {
        super(new SpinnerNumberModel(value, min, max, step));
        Objects.requireNonNull(controlUpdateCallback);
        addChangeListener(changeEvent -> controlUpdateCallback.onUpdate(this));
    }

    @Override
    public Object getControlValue() {
        return getValue();
    }

    public int getIntegerValue() {
        return (int) getControlValue();
    }
}