package org.bfg.gui.tabs.file;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.GlyphInfo;
import org.bfg.gui.custom.BitmapFontPanel;
import org.bfg.gui.tabs.file.character.CharView;
import org.bfg.gui.tabs.file.property.PropertyView;
import org.bfg.gui.tabs.file.property.controls.ControlValueChangeObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class FileTabView extends JPanel {

    private IFileTabPresenter presenter = new IFileTabPresenter() {
        @Override
        public void onSelectGlyph(GlyphInfo selection) {}
        @Override
        public void onChangeProperty() {}
    };
    private final PropertyView propertyView;
    private final BitmapFontPanel bitmapFontPanel;
    private final CharView charView;

    public FileTabView() {
        super(new BorderLayout());

        this.charView = new CharView();
        add(this.charView, BorderLayout.LINE_END);

        this.propertyView = new PropertyView(new ControlValueChangeObserver(this.presenter::onChangeProperty));
        this.bitmapFontPanel = new BitmapFontPanel(this.presenter::onSelectGlyph);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.propertyView,
            this.bitmapFontPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0);
        splitPane.setDividerSize(6);
        add(splitPane, BorderLayout.CENTER);
    }

    public void setPresenter(IFileTabPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.presenter = presenter;
    }

    public IFileTabPresenter getPresenter() {
        return this.presenter;
    }

    // TODO
    public void setShowGrid(boolean flag) {
        this.bitmapFontPanel.setShowGrid(flag);
    }

    public void setBitmapFont(BitmapFont font) {
        this.bitmapFontPanel.setBitmapFont(font);
        this.charView.setBitmapFont(font);
    }

    public PropertyView getPropertyView() {
        return this.propertyView;
    }

    public interface IFileTabPresenter {
        void onSelectGlyph(GlyphInfo selection);
        void onChangeProperty();
    }
}
