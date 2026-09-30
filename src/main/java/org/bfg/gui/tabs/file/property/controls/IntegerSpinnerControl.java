package org.bfg.gui.tabs.file.property.controls;

import javax.swing.*;
import java.util.Objects;

/**
 * A {@link IValueControl} component based on a {@link JSpinner} with a {@link SpinnerNumberModel}. Supports a callback
 * when the value changes.
 */
public final class IntegerSpinnerControl extends JSpinner implements IValueControl {

    /**
     * Creates the control with the given options.
     *
     * @param value                 The initial value of the spinner.
     * @param min                   The minimum value of the spinner.
     * @param max                   The maximum value of the spinner.
     * @param step                  The step size to change the spinner.
     * @param controlUpdateCallback The callback when the value changes, must not be {@code null}.
     */
    public IntegerSpinnerControl(int value, int min, int max, int step, IControlUpdateCallback controlUpdateCallback) {
        super(new SpinnerNumberModel(value, min, max, step));
        Objects.requireNonNull(controlUpdateCallback);
        addChangeListener(changeEvent -> controlUpdateCallback.onUpdate(this));
    }

    @Override
    public Object getControlValue() {
        return getValue();
    }

    /**
     * Returns the controls value as an integer.
     *
     * @return The integer value of this control.
     */
    public int getIntegerValue() {
        return (int) getControlValue();
    }
}