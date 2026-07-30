package org.bfg.gui.tabs.file;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.Export;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphInfo;
import org.bfg.generate.GlyphRange;
import org.bfg.gui.tabs.file.property.PropertyView;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    @Override
    public void doActionExport() {
        /*final JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".png") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Image (*.png)";
            }
        });
        fileChooser.setAcceptAllFileFilterUsed(false);

        final int action = fileChooser.showDialog(this, "Export font");
        if (action != JFileChooser.APPROVE_OPTION)
            return;

        final File imageFile = fileChooser.getSelectedFile();
        final BitmapFont bitmapFont = openedTab.getBitmapFont(); // -> Here we need the view controller?

        try {
            Export.export(imageFile, bitmapFont);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }*/
    }
}
