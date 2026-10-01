package org.bfg.gui.tabs.file.property.controls;

import javax.swing.*;
import java.util.Objects;

/**
 * A {@link IValueControl} component based on a {@link JCheckBox}. Supports a callback when the value changes.
 */
public final class BooleanCheckBoxControl extends JCheckBox implements IValueControl {

    /**
     * Create the control with the given listener for value changes.
     *
     * @param controlUpdateCallback The callback to handle changed values, must not be {@code null}.
     */
    public BooleanCheckBoxControl(IControlUpdateCallback controlUpdateCallback) {
        super();
        Objects.requireNonNull(controlUpdateCallback);
        addChangeListener(changeEvent -> controlUpdateCallback.onUpdate(this));
    }

    @Override
    public Object getControlValue() {
        return isSelected();
    }

    /**
     * Returns the controls value as a boolean.
     *
     * @return The boolean value of this control.
     */
    public boolean getBooleanValue() {
        return (boolean) getControlValue();
    }
}