package org.bfg.gui.tabs.welcome;

import org.bfg.gui.IGuiPresenter;

import javax.swing.*;
import java.util.Objects;

/**
 * The presenter for a single {@link WelcomeTabView}.
 */
public final class WelcomeTabPresenter implements WelcomeTabView.IWelcomeTabPresenter {

    private final WelcomeTabView view;
    private final IGuiPresenter guiPresenter;

    /**
     * Binds this presenter to the given {@code view}.
     *
     * @param view         The {@link WelcomeTabView} to present, must not be {@code null}.
     * @param guiPresenter The parent presenter for the GUI, must not be {@code null}.
     */
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

    @Override
    public void setShowUvCoordinates(boolean flag) {

    }
}
