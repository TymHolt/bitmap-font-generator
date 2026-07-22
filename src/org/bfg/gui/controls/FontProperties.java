package org.bfg.gui.controls;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FontProperties {

    private final JComponent componentContainer;
    private final Runnable onChange;
    private final List<Control> controlList;

    private final Runnable onAction = new Runnable() {
        @Override
        public void run() {
            boolean hasChanged = false;
            for (Control control : controlList) {
                control.setHasChanged(false);
                control.readComponentValue();
                hasChanged = hasChanged || control.hasChanged();
            }

            if (hasChanged)
                onChange.run();
        }
    };

    public FontProperties(JComponent componentContainer, Runnable onChange) {
        Objects.requireNonNull(componentContainer);
        this.componentContainer = componentContainer;

        Objects.requireNonNull(onChange);
        this.onChange = onChange;

        this.controlList = new ArrayList<>();
    }

    private void addLabel(String label) {
        Objects.requireNonNull(label);
        this.componentContainer.add(new JLabel(" " + label + ": "));
    }

    public IStringValue addComboBox(String label, String[] values) {
        Objects.requireNonNull(values);
        final StringComboBoxControl comboBoxControl = new StringComboBoxControl(this.onAction, values);
        this.controlList.add(comboBoxControl);

        addLabel(label);
        this.componentContainer.add(comboBoxControl.getComponent());
        return comboBoxControl;
    }

    public IIntegerValue addSpinner(String label, int value, int min, int max, int stepSize) {
        final IntegerSpinnerControl integerSpinnerControl = new IntegerSpinnerControl(this.onAction,
                value, min, max, stepSize);
        this.controlList.add(integerSpinnerControl);

        addLabel(label);
        this.componentContainer.add(integerSpinnerControl.getComponent());
        return integerSpinnerControl;
    }

    public IBooleanValue addCheckBox(String label) {
        final BooleanCheckBoxControl booleanCheckBoxControl = new BooleanCheckBoxControl(this.onAction);
        this.controlList.add(booleanCheckBoxControl);

        addLabel(label);
        this.componentContainer.add(booleanCheckBoxControl.getComponent());
        return booleanCheckBoxControl;
    }

    public void updateControls() {
        this.onAction.run();
    }
}
