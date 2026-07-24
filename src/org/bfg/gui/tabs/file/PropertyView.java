package org.bfg.gui.tabs.file;

import javax.swing.*;
import java.awt.*;

final class PropertyView extends JPanel {

    PropertyView() {
        setPreferredSize(new Dimension(250, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createRow("Font Name", new JComboBox<>(new String[]{"A", "B"})));
        add(createRow("Font Style", new JComboBox<>(new String[]{"A", "B"})));
        add(createRow("Font Size", new JSpinner(new SpinnerNumberModel(0, 0, 100, 1))));
        add(createRow("Range Begin", new JSpinner(new SpinnerNumberModel(0, 0, 100, 1))));
        add(createRow("Range End", new JSpinner(new SpinnerNumberModel(0, 0, 100, 1))));
        add(createRow("Anti-Alias", new JCheckBox()));

        add(Box.createVerticalGlue());
    }

    private JPanel createRow(String label, JComponent component) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(90, jLabel.getPreferredSize().height));

        row.add(jLabel, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);

        return row;
    }
}