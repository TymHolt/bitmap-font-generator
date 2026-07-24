package org.bfg.gui.tabs.file;

import org.bfg.Context;
import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphRange;
import org.bfg.gui.controls.FontProperties;
import org.bfg.gui.controls.IBooleanValue;
import org.bfg.gui.controls.IIntegerValue;
import org.bfg.gui.controls.IStringValue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class FileTabView extends JPanel {

    private final Context context;
    private final FontView fontView;
    private final IStringValue fontName;
    private final IStringValue fontStyle;
    private final IIntegerValue fontSize;
    private final IIntegerValue rangeBegin;
    private final IIntegerValue rangeEnd;
    private final IBooleanValue antiAlias;

    public FileTabView(Context context) {
        super();
        setLayout(new BorderLayout());
        this.context = context;

        final PropertyView propertyView = new PropertyView();
        //add(propertyView, BorderLayout.LINE_START);

        this.fontView = new FontView(this.context);
        //add(this.fontView, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                propertyView,
                this.fontView
        );

        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0);
        splitPane.setDividerSize(6);

        add(splitPane, BorderLayout.CENTER);

        // TODO Legacy
        // --------------------------------------------------------------------------------------------
        final JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.LINE_AXIS));

        final FontProperties fontProperties = new FontProperties(topBar, this::generateFont);
        this.fontName = fontProperties.addComboBox("Font", getAllFontNames());
        this.fontStyle = fontProperties.addComboBox("Style", new String[] {"Plain", "Bold", "Italic"});
        this.fontSize = fontProperties.addSpinner("Size", 10, 1, 100, 1); // TODO Integer.MAX_VALUE?
        this.rangeBegin = fontProperties.addSpinner("Begin ID", 0, 0, Character.MAX_VALUE, 1);
        this.rangeEnd = fontProperties.addSpinner("End ID", 255, 0, Character.MAX_VALUE, 1);
        this.antiAlias = fontProperties.addCheckBox("Anti-Alias");
        fontProperties.updateControls();
        // --------------------------------------------------------------------------------------------

        generateFont();
    }

    public BitmapFont getBitmapFont() {
        return this.fontView.getBitmapFont();
    }

    private void generateFont() {
        final String name = this.fontName.getValue();
        final String style = this.fontStyle.getValue();
        final int size = this.fontSize.getValue();
        final int rangeBegin = this.rangeBegin.getValue();
        final int rangeEnd = this.rangeEnd.getValue();
        final boolean antiAlias = this.antiAlias.getValue();

        if (name == null || style == null)
            return;

        final Font font = FontStyle.newFontWithStyle(name, style, size);

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
