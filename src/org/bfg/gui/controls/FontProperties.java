package org.bfg.gui.controls;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public final class FontProperties {

    private final JComponent componentContainer;
    private final Runnable onChange;

    public FontProperties(JComponent componentContainer, Runnable onChange) {
        Objects.requireNonNull(componentContainer);
        this.componentContainer = componentContainer;

        Objects.requireNonNull(onChange);
        this.onChange = onChange;
    }

    private void addLabel(String label) {
        Objects.requireNonNull(label);
        this.componentContainer.add(new JLabel(" " + label + ": "));
    }

    private class StringValueMemorizer implements ActionListener, IStringValue {

        private String currentValue = null;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            final String newValue = (String) ((JComboBox<String>) actionEvent.getSource()).getSelectedItem();
            if (newValue == null)
                return;

            final boolean hasChanged = !newValue.equals(this.currentValue);
            this.currentValue = newValue;

            if (hasChanged)
                checkChanges();
        }

        @Override
        public String getValue() {
            return this.currentValue;
        }
    }

    public IStringValue addComboBox(String label, String[] values) {
        Objects.requireNonNull(values);

        addLabel(label);

        final StringValueMemorizer stringValueMemorizer = new StringValueMemorizer();
        final JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.addActionListener(stringValueMemorizer);

        this.componentContainer.add(comboBox);
        return stringValueMemorizer;
    }

    private class IntegerValueMemorizer implements ChangeListener, IIntegerValue {

        private int currentValue = 0;

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            final int newValue = (int) ((JSpinner) changeEvent.getSource()).getValue();

            final boolean hasChanged = newValue != this.currentValue;
            this.currentValue = newValue;

            if (hasChanged)
                checkChanges();
        }

        @Override
        public int getValue() {
            return this.currentValue;
        }
    }

    public IIntegerValue addSpinner(String label, int initialValue, int minimum, int maximum, int stepSize) {
        addLabel(label);

        final IntegerValueMemorizer integerValueMemorizer = new IntegerValueMemorizer();
        final JSpinner spinner = new JSpinner(new SpinnerNumberModel(initialValue, minimum, maximum, stepSize));
        spinner.addChangeListener(integerValueMemorizer);

        this.componentContainer.add(spinner);
        return integerValueMemorizer;
    }

    private class BooleanValueMemorizer implements ChangeListener, IBooleanValue {

        private boolean currentValue = false;

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            final boolean newValue = ((JCheckBox) changeEvent.getSource()).isSelected();

            final boolean hasChanged = newValue != this.currentValue;
            this.currentValue = newValue;

            if (hasChanged)
                checkChanges();
        }

        @Override
        public boolean getValue() {
            return this.currentValue;
        }
    }

    public IBooleanValue addCheckBox(String label) {
        addLabel(label);

        final BooleanValueMemorizer booleanValueMemorizer = new BooleanValueMemorizer();
        final JCheckBox checkBox = new JCheckBox();
        checkBox.addChangeListener(booleanValueMemorizer);

        this.componentContainer.add(checkBox);
        return booleanValueMemorizer;
    }

    private void checkChanges() {
        // TODO Check has something changed? If yes run onChange
    }
}
