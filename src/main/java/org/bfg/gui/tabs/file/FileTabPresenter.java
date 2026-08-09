package org.bfg.gui.tabs.file;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.Export;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphInfo;
import org.bfg.generate.GlyphRange;
import org.bfg.gui.IGuiPresenter;
import org.bfg.gui.tabs.file.dialog.ExportDialog;
import org.bfg.gui.tabs.file.property.PropertyView;

import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public final class FileTabPresenter implements FileTabView.IFileTabPresenter {

    private final FileTabView view;
    private final IGuiPresenter guiPresenter;
    private BitmapFont font;

    public FileTabPresenter(FileTabView view, IGuiPresenter guiPresenter) {
        Objects.requireNonNull(view);
        this.view = view;

        Objects.requireNonNull(guiPresenter);
        this.guiPresenter = guiPresenter;

        generateFont();
    }

    private void generateFont() {
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
        this.font = BitmapFontGenerator.generate(font, range, antiAlias);
        this.view.setBitmapFont(this.font);

        final BufferedImage atlasImage = this.font.getAtlasImage();
        final int width = atlasImage.getWidth();
        final int height = atlasImage.getHeight();
        this.guiPresenter.onRenameTab(name + " (" + width + "x" + height + ")");

        this.view.invalidate();
        this.view.repaint();
    }

    public void onSelectGlyph(GlyphInfo selection) {
        this.view.getGlyphView().setSelection(selection);
    }

    public void onChangeProperty() {
        generateFont();
    }

    @Override
    public void doActionExport() {
        final ExportDialog dialog = new ExportDialog(this.guiPresenter.getGuiParent(), "Export Bitmap Font");
        dialog.setVisible(true);
        final ExportDialog.ExportDialogResult result = dialog.getResult();
        if (!result.confirmed)
            return;

        try {
            // TODO Ask if overwrite
            Export.export(result.imageFile, result.dataFile, this.font);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this.view, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void setShowGrid(boolean flag) {
        this.view.setShowGrid(flag);
    }
}
