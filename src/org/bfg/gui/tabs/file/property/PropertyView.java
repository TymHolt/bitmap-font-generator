package org.bfg.gui.tabs.file.property;

import org.bfg.generate.FontStyle;
import org.bfg.gui.tabs.file.property.controls.BooleanCheckBoxControl;
import org.bfg.gui.tabs.file.property.controls.ControlValueChangeObserver;
import org.bfg.gui.tabs.file.property.controls.IntegerSpinnerControl;
import org.bfg.gui.tabs.file.property.controls.StringComboBoxControl;

import javax.swing.*;
import java.awt.*;

public final class PropertyView extends JPanel {

    private final StringComboBoxControl fontNameControl;
    private final StringComboBoxControl fontStyleControl;
    private final IntegerSpinnerControl fontSizeControl;
    private final IntegerSpinnerControl rangeBeginControl;
    private final IntegerSpinnerControl rangeEndControl;
    private final BooleanCheckBoxControl antiAliasControl;

    public PropertyView(ControlValueChangeObserver controlValueChangeObserver) {
        setPreferredSize(new Dimension(250, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.fontNameControl = new StringComboBoxControl(getAllFontNames(), controlValueChangeObserver);
        add(createRow("Font Name", this.fontNameControl));

        this.fontStyleControl = new StringComboBoxControl(FontStyle.getAllValues(), controlValueChangeObserver);
        add(createRow("Font Style", this.fontStyleControl));

        this.fontSizeControl = new IntegerSpinnerControl(10, 1, Integer.MAX_VALUE, 1, controlValueChangeObserver);
        add(createRow("Font Size", this.fontSizeControl));

        this.rangeBeginControl = new IntegerSpinnerControl(0, 0, Character.MAX_VALUE, 1, controlValueChangeObserver);
        add(createRow("Range Begin", this.rangeBeginControl));

        this.rangeEndControl = new IntegerSpinnerControl(255, 0, Character.MAX_VALUE, 1, controlValueChangeObserver);
        add(createRow("Range End", this.rangeEndControl));

        this.antiAliasControl = new BooleanCheckBoxControl(controlValueChangeObserver);
        add(createRow("Anti-Alias", this.antiAliasControl));

        add(Box.createVerticalGlue());
    }

    public String getFontName() {
        return this.fontNameControl.getStringValue();
    }

    public String getFontStyle() {
        return this.fontStyleControl.getStringValue();
    }

    public int getFontSize() {
        return this.fontSizeControl.getIntegerValue();
    }

    public char getRangeBegin() {
        return (char) this.rangeBeginControl.getIntegerValue();
    }

    public char getRangeEnd() {
        return (char) this.rangeEndControl.getIntegerValue();
    }

    public boolean getAntiAlias() {
        return this.antiAliasControl.getBooleanValue();
    }

    private JPanel createRow(String label, JComponent component) {
        final JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        final JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(90, labelComponent.getPreferredSize().height));

        row.add(labelComponent, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);
        return row;
    }

    private static String[] getAllFontNames() {
        final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final Font[] fonts = graphicsEnvironment.getAllFonts();
        final String[] names = new String[fonts.length];

        for (int index = 0; index < fonts.length; index++)
            names[index] = fonts[index].getFontName();

        return names;
    }
}