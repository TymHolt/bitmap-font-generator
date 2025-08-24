package org.bfg.form.base;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class InputForm extends Form {

    private record Input(JLabel label, JComponent component) {

    }

    private ArrayList<Input> inputs;
    private ArrayList<JButton> actions;

    @Override
    protected void initForm() {
        this.inputs = new ArrayList<>();
        this.actions = new ArrayList<>();

        this.initInputs();

        final int padding = 5;
        final int rowHeight = 20;
        final int labelWidth = 80;
        final int inputWidth = 200;

        int y = padding;
        for (Input input : this.inputs) {
            int x = padding;
            input.label.setBounds(x, y, labelWidth, rowHeight);
            this.add(input.label);

            x += labelWidth + padding;
            input.component.setBounds(x, y, inputWidth, rowHeight);
            this.add(input.component);

            y += rowHeight + padding;
        }

        final int totalWidth = padding + labelWidth + padding + inputWidth + padding;
        final int actionWidth = 100;

        int x = totalWidth - padding - actionWidth;
        for (JButton action : this.actions) {
            action.setBounds(x, y, actionWidth, rowHeight);
            this.add(action);

            x -= actionWidth - padding;
        }

        y += rowHeight + padding;

        this.inputs = null;
        this.actions = null;

        setContentSize(totalWidth, y);
    }

    protected abstract void initInputs();

    protected JTextField addTextInput(String label) {
        final JTextField textField = new JTextField();
        this.inputs.add(new Input(new JLabel(label), textField));
        return textField;
    }

    protected JComboBox<String> addSelectionInput(String label, String[] options) {
        final JComboBox<String> comboBox = new JComboBox<>(options);
        this.inputs.add(new Input(new JLabel(label), comboBox));
        return comboBox;
    }

    protected JSpinner addNumberInput(String label, int value, int min, int max) {
        final JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
        this.inputs.add(new Input(new JLabel(label), spinner));
        return spinner;
    }

    protected JButton addAction(String label, ActionListener actionListener) {
        final JButton button = new JButton(label);
        button.addActionListener(actionListener);
        this.actions.add(button);
        return button;
    }
}
