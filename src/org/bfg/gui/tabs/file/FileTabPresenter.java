package org.bfg.gui.tabs.file;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphInfo;
import org.bfg.generate.GlyphRange;
import org.bfg.gui.tabs.file.property.PropertyView;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class FileTabPresenter implements FileTabView.IFileTabPresenter {

    private final FileTabView view;

    public FileTabPresenter(FileTabView view) {
        Objects.requireNonNull(view);
        this.view = view;
    }

    public void onSelectGlyph(GlyphInfo selection) {
        // TODO
    }

    public void onChangeProperty() {
        final PropertyView propertyView = this.view.getPropertyView();
        final String name = propertyView.getFontName();
        final String style = propertyView.getFontStyle();
        final int size = propertyView.getFontSize();
        final char rangeBegin = propertyView.getRangeBegin();
        final char rangeEnd = propertyView.getRangeEnd();
        final boolean antiAlias = propertyView.getAntiAlias();

        if (name == null || style == null)
            return;

        final Font font = FontStyle.newFontWithStyle(name, style, size);

        final GlyphRange range = new GlyphRange(rangeBegin, rangeEnd);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias);
        this.view.setBitmapFont(bitmapFont);

        final BufferedImage atlasImage = bitmapFont.getAtlasImage();
        final int width = atlasImage.getWidth();
        final int height = atlasImage.getHeight();

        // TODO How can we get this to work?
        // this.context.renameCurrentTab(name + " (" + width + "x" + height + ")");
    }
}
