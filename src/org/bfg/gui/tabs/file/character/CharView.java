package org.bfg.gui.tabs.file.character;

import org.bfg.generate.BitmapFont;
import org.bfg.gui.tabs.file.font.ICharSelectionCallback;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

public final class CharView extends JPanel implements ICharSelectionCallback {

    private final JLabel labelId;
    private final JLabel labelChar;
    private final JLabel labelX;
    private final JLabel labelY;
    private final JLabel labelWidth;
    private final JLabel labelHeight;
    private final SubImageView subImageView;
    private BitmapFont bitmapFont;

    public CharView() {
        setPreferredSize(new Dimension(250, 0));
        setMinimumSize(new Dimension(0, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.labelId = new JLabel("");
        add(createRow("ID", this.labelId));

        this.labelChar = new JLabel("");
        add(createRow("Char", this.labelChar));

        this.labelX = new JLabel("");
        add(createRow("X", this.labelX));

        this.labelY = new JLabel("");
        add(createRow("Y", this.labelY));

        this.labelWidth = new JLabel("");
        add(createRow("Width", this.labelWidth));

        this.labelHeight = new JLabel("");
        add(createRow("Height", this.labelHeight));

        this.subImageView = new SubImageView();
        add(createRow(" ", this.subImageView, 3));

        add(Box.createVerticalGlue());
    }

    private JPanel createRow(String label, JComponent component) {
        return createRow(label, component, 1);
    }

    private JPanel createRow(String label, JComponent component, int heightFactor) {
        final JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40 * heightFactor));

        final JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(90, labelComponent.getPreferredSize().height * heightFactor));

        row.add(labelComponent, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);
        return row;
    }

    @Override
    public void onSelectChar(char c, Rectangle bounds) {
        this.labelId.setText(Integer.toString(c));
        this.labelChar.setText(Character.toString(c));
        this.labelX.setText(Integer.toString(bounds.x));
        this.labelY.setText(Integer.toString(bounds.y));
        this.labelWidth.setText(Integer.toString(bounds.width));
        this.labelHeight.setText(Integer.toString(bounds.height));

        if (this.bitmapFont != null)
            this.subImageView.setSubImage(bounds, this.bitmapFont.getAtlasImage());
    }

    @Override
    public void onClearSelection() {
        this.labelId.setText("");
        this.labelChar.setText("");
        this.labelX.setText("");
        this.labelY.setText("");
        this.labelWidth.setText("");
        this.labelHeight.setText("");
        this.subImageView.resetSubImage();
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }
}