package org.bfg.gui.tabs.file.property.controls;

import javax.swing.*;
import java.util.Objects;

public final class BooleanCheckBoxControl extends JCheckBox implements IValueControl {

    public BooleanCheckBoxControl(IControlUpdateCallback controlUpdateCallback) {
        super();
        Objects.requireNonNull(controlUpdateCallback);
        addChangeListener(changeEvent -> controlUpdateCallback.onUpdate(this));
    }

    @Override
    public Object getControlValue() {
        return isSelected();
    }

    public boolean getBooleanValue() {
        return (boolean) getControlValue();
    }
}