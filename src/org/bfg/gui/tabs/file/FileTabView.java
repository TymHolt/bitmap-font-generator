package org.bfg.gui.tabs.file;

import org.bfg.Context;
import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphRange;
import org.bfg.gui.tabs.file.property.PropertyView;
import org.bfg.gui.tabs.file.property.controls.ControlValueChangeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class FileTabView extends JPanel {

    private final Context context;
    private final PropertyView propertyView;
    private final FontView fontView;

    public FileTabView(Context context) {
        super();
        setLayout(new BorderLayout());
        this.context = context;

        this.propertyView = new PropertyView(new ControlValueChangeObserver(this::generateFont));
        this.fontView = new FontView(this.context);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.propertyView, this.fontView);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0);
        splitPane.setDividerSize(6);
        add(splitPane, BorderLayout.CENTER);

        generateFont();
    }

    public BitmapFont getBitmapFont() {
        return this.fontView.getBitmapFont();
    }

    private void generateFont() {
        final String name = this.propertyView.getFontName();
        final String style = this.propertyView.getFontStyle();
        final int size = this.propertyView.getFontSize();
        final char rangeBegin = this.propertyView.getRangeBegin();
        final char rangeEnd = this.propertyView.getRangeEnd();
        final boolean antiAlias = this.propertyView.getAntiAlias();

        if (name == null || style == null)
            return;

        final Font font = FontStyle.newFontWithStyle(name, style, size);

        final GlyphRange range = new GlyphRange(rangeBegin, rangeEnd);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias);
        this.fontView.setFont(bitmapFont);

        final BufferedImage atlasImage = bitmapFont.getAtlasImage();
        final int width = atlasImage.getWidth();
        final int height = atlasImage.getHeight();
        this.context.renameCurrentTab(name + " (" + width + "x" + height + ")");
    }
}
