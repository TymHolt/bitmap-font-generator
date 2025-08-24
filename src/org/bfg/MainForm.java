package org.bfg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class MainForm extends JFrame {

    private final JTextField fieldFontName = new JTextField();
    private final JComboBox<String> fieldFontStyle = new JComboBox<>(new String[] {"Plain", "Bold", "Italic"});
    private final JSpinner fieldFontSize = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
    private final JButton buttonGenerate = new JButton("Generate");
    private final JButton buttonPreview = new JButton("Preview");
    private BufferedImage preview;

    private final int columnPadding = 5;
    private final int labelWidth = 80;
    private final int componentWidth = 200;
    private final int rowHeight = 20;
    private final int rowPadding = 5;
    private final int columnWidth = columnPadding * 3 + labelWidth + componentWidth;
    private final int buttonWidth = 100;

    private int rowY = rowPadding;
    private int columnX = columnWidth - rowPadding;

    public MainForm() {
        super("Bitmap Font Generator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.addLabeled("Font Name", this.fieldFontName);
        this.addLabeled("Font Style", this.fieldFontStyle);
        this.addLabeled("Font Size", this.fieldFontSize);

        this.addButton(this.buttonPreview);
        this.addButton(this.buttonGenerate);

        this.setSize(this.columnWidth, this.rowY + 60);
        this.setVisible(true);
    }

    private void addLabeled(String label, JComponent component) {
        final JLabel labelComponent = new JLabel(label);
        labelComponent.setBounds(this.columnPadding, this.rowY, this.labelWidth, this.rowHeight);
        this.add(labelComponent);

        component.setBounds(this.columnPadding * 2 + this.labelWidth,
            this.rowY, this.componentWidth, this.rowHeight);
        this.add(component);

        this.rowY += this.rowPadding + this.rowHeight;
    }

    private void addButton(JButton button) {
        button.setBounds(this.columnX - this.buttonWidth, this.rowY,
            this.buttonWidth, this.rowHeight);
        this.add(button);
        this.columnX -= this.buttonWidth - this.columnPadding;
    }

    @Override
    public void paint(Graphics graphics) {
        this.buttonPreview.setEnabled(this.preview != null);
        super.paint(graphics);
    }
}
