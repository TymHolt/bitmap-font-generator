package org.bfg.gui;

import org.bfg.Context;
import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphRange;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

final class FileView extends JPanel {

    private final Context context;
    private final JComboBox<String> nameSelection;
    private final JComboBox<String> styleSelection;
    private final JSpinner sizeSelection;
    private final JSpinner beginSelection;
    private final JSpinner endSelection;
    private final JCheckBox antiAlias;

    private final BitmapFontView fontView;
    private String lastName;
    private String lastStyle;
    private int lastSize;
    private int lastBegin;
    private int lastEnd;
    private boolean lastAntiAlias;

    FileView(Context context) {
        super();
        setLayout(new BorderLayout());
        this.context = context;

        // ---------------------------------------

        final JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.LINE_AXIS));

        this.nameSelection = new JComboBox<>(getAllFontNames());
        this.nameSelection.addActionListener(actionEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Font: "));
        topBar.add(this.nameSelection);

        this.styleSelection = new JComboBox<>(
            new String[] {"Plain", "Bold", "Italic"});
        this.styleSelection.addActionListener(actionEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Style: "));
        topBar.add(this.styleSelection);

        this.sizeSelection = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        this.sizeSelection.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Size: "));
        topBar.add(this.sizeSelection);

        this.beginSelection = new JSpinner(new SpinnerNumberModel(0, 0, Character.MAX_VALUE, 1));
        beginSelection.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" ID-Start: "));
        topBar.add(beginSelection);

        this.endSelection = new JSpinner(new SpinnerNumberModel(256, 0, Character.MAX_VALUE, 1));
        endSelection.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" ID-End: "));
        topBar.add(endSelection);

        this.antiAlias = new JCheckBox();
        this.antiAlias.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Anti-Alias: "));
        topBar.add(this.antiAlias);

        add(topBar, BorderLayout.PAGE_START);

        // ---------------------------------------

        this.fontView = new BitmapFontView(this.context);
        add(this.fontView, BorderLayout.CENTER);

        this.lastName = "";
        this.lastStyle = "";
        this.lastSize = 0;
        this.lastBegin = 0;
        this.lastEnd = 0;
        this.lastAntiAlias = false;
        generateFont();
    }

    public BitmapFont getBitmapFont() {
        return this.fontView.getBitmapFont();
    }

    private void generateFont() {
        final String name = (String) this.nameSelection.getSelectedItem();
        final String style = (String) this.styleSelection.getSelectedItem();
        final int size = (Integer) this.sizeSelection.getValue();
        final int rangeBegin = (Integer) this.beginSelection.getValue();
        final int rangeEnd = (Integer) this.endSelection.getValue();
        final boolean antiAlias = this.antiAlias.isSelected();

        if (name == null || style == null)
            return;

        if (name.equals(this.lastName) && style.equals(this.lastStyle) && size == this.lastSize &&
            rangeBegin == this.lastBegin && rangeEnd == this.lastEnd && antiAlias == this.lastAntiAlias)
            return;

        this.lastName = name;
        this.lastStyle = style;
        this.lastSize = size;
        this.lastBegin = rangeBegin;
        this.lastEnd = rangeEnd;
        this.lastAntiAlias = antiAlias;

        final Font font = new Font(name, FontStyle.getId(style), size);

        final GlyphRange range = new GlyphRange((char) rangeBegin, (char) rangeEnd);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias);
        this.fontView.setFont(bitmapFont);

        final BufferedImage atlasImage = bitmapFont.getAtlasImage();
        final int width = atlasImage.getWidth();
        final int height = atlasImage.getHeight();
        this.context.renameCurrentTab(name + " (" + width + "x" + height + ")");
    }

    private static String[] getAllFontNames() {
        final GraphicsEnvironment graphicsEnvironment =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        final Font[] fonts = graphicsEnvironment.getAllFonts();
        final String[] names = new String[fonts.length];

        for (int index = 0; index < fonts.length; index++)
            names[index] = fonts[index].getFontName();

        return names;
    }
}
