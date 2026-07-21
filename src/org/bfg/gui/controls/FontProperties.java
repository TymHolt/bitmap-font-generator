package org.bfg.gui.controls;

import javax.swing.*;
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

    public IStringValue addComboBox(String label, String[] values) {
        Objects.requireNonNull(values);

        addLabel(label);

        final JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.addActionListener(actionEvent -> {
            checkChanges();
        });
        this.componentContainer.add(comboBox);

        return () -> (String) comboBox.getSelectedItem();
    }

    public IIntegerValue addSpinner(String label, int initialValue, int minimum, int maximum, int stepSize) {
        addLabel(label);

        final JSpinner spinner = new JSpinner(new SpinnerNumberModel(initialValue, minimum, maximum, stepSize));
        spinner.addChangeListener(changeEvent -> {
            checkChanges();
        });
        this.componentContainer.add(spinner);

        return () -> (Integer) spinner.getValue();
    }

    public IBooleanValue addCheckBox(String label) {
        addLabel(label);

        final JCheckBox checkBox = new JCheckBox();
        checkBox.addChangeListener(changeEvent -> {
            checkChanges();
        });
        this.componentContainer.add(checkBox);

        return () -> checkBox.isSelected();
    }

    private void checkChanges() {
        // TODO Check has something changed? If yes run onChange
    }
}
