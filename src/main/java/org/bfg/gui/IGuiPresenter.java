package org.bfg.gui;

import org.bfg.gui.tabs.TabView;

import javax.swing.JFrame;

/**
 * The presenter interface for the GUI.
 */
public interface IGuiPresenter {

    /**
     * Called by the tab presenter when a new file view should be opened.
     */
    void onOpenNewFile();

    /**
     * Called by the tab presenter when a tab should be renamed.
     *
     * @param view  The view to rename the title of, must not be {@code null}.
     * @param title The new title, must not be {@code null}.
     */
    void onRenameTab(TabView view, String title);

    /**
     * Called by the tab presenter when a tab should be closed.
     *
     * @param view The view to close, must not be {@code null}.
     */
    void onTabClose(TabView view);

    /**
     * Returns the main {@link JFrame} the GUI is in. May be needed for dialogs.
     *
     * @return The frame containing the GUI.
     */
    JFrame getGuiParent(); // TODO This can be cleaner...
}
