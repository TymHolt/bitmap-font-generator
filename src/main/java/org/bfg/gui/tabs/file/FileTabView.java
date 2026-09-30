package org.bfg.gui.tabs.file;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.GlyphInfo;
import org.bfg.gui.custom.BitmapFontPanel;
import org.bfg.gui.tabs.TabView;
import org.bfg.gui.tabs.file.character.GlyphView;
import org.bfg.gui.tabs.file.property.PropertyView;
import org.bfg.gui.tabs.file.property.controls.ControlValueChangeObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A view component for one {@link BitmapFont} file tab.
 */
public final class FileTabView extends TabView {

    /**
     * The presenter interface.
     */
    public interface IFileTabPresenter extends ITabPresenter {
        /**
         * Will be called when the selected glyph changed.
         *
         * @param selection The new selected glyph or {@code null} to clear.
         */
        void onSelectGlyph(GlyphInfo selection);

        /**
         * Will be called a generation property value changed.
         */
        void onChangeProperty();
    }
    private IFileTabPresenter presenter = new IFileTabPresenter() {
        @Override
        public void onSelectGlyph(GlyphInfo selection) {}
        @Override
        public void onChangeProperty() {}
        @Override
        public void doActionExport() {}
        @Override
        public void setShowGrid(boolean flag) {}
        @Override
        public void setShowUvCoordinates(boolean flag) {}
    };
    private final PropertyView propertyView;
    private final BitmapFontPanel bitmapFontPanel;
    private final GlyphView glyphView;

    /**
     * Builds the view with a placeholder presenter. The presenter should get this instance passed and set itself using
     * {@link #setPresenter(IFileTabPresenter)}.
     */
    public FileTabView() {
        super(new BorderLayout());

        this.glyphView = new GlyphView();
        add(this.glyphView, BorderLayout.LINE_END);

        this.propertyView = new PropertyView(new ControlValueChangeObserver(() -> this.presenter.onChangeProperty()));
        this.bitmapFontPanel = new BitmapFontPanel(selection -> this.presenter.onSelectGlyph(selection));
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.propertyView,
            this.bitmapFontPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0);
        splitPane.setDividerSize(6);
        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Set the presenter for this view.
     *
     * @param presenter The presenter to use, must not be {@code null}.
     */
    public void setPresenter(IFileTabPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.presenter = presenter;
    }

    @Override
    public ITabPresenter getPresenter() {
        return this.presenter;
    }

    /**
     * Set if the font should be displayed with a grid outlining glyph areas.
     *
     * @param flag {@code true} to show the grid.
     */
    public void setShowGrid(boolean flag) {
        this.bitmapFontPanel.setShowGrid(flag);
    }

    /**
     * Set the (newly generated) font instance to display.
     *
     * @param font The generated font.
     */
    public void setBitmapFont(BitmapFont font) {
        this.bitmapFontPanel.setBitmapFont(font);
        this.glyphView.setBitmapFont(font);
    }

    /**
     * @return The underlying instance if the {@link PropertyView}.
     */
    public PropertyView getPropertyView() {
        return this.propertyView;
    }

    /**
     * @return The underlying instance of the {@link GlyphView}.
     */
    public GlyphView getGlyphView() {
        return this.glyphView;
    }
}
