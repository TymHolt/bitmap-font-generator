package org.bfg.gui.tabs.welcome;

import org.bfg.gui.IGuiPresenter;

import javax.swing.*;
import java.util.Objects;

public final class WelcomeTabPresenter implements WelcomeTabView.IWelcomeTabPresenter {

    private final WelcomeTabView view;
    private final IGuiPresenter guiPresenter;

    public WelcomeTabPresenter(WelcomeTabView view, IGuiPresenter guiPresenter) {
        Objects.requireNonNull(view);
        this.view = view;

        Objects.requireNonNull(guiPresenter);
        this.guiPresenter = guiPresenter;
    }

    @Override
    public void onOpenNewFile() {
        this.guiPresenter.onOpenNewFile();
        this.guiPresenter.onTabClose(this.view);
    }

    @Override
    public void doActionExport() {
        JOptionPane.showMessageDialog(view, "Open a font to export", "Export", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setShowGrid(boolean flag) {

    }
}
