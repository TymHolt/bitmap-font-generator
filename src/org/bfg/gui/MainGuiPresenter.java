package org.bfg.gui;

import java.util.Objects;

public final class MainGuiPresenter implements MainGui.IMainGuiPresenter {

    private final MainGui gui;

    public MainGuiPresenter(MainGui gui) {
        Objects.requireNonNull(gui);
        this.gui = gui;
    }

    @Override
    public void onOpenNewFile() {
        this.gui.newFileTab("New");
    }

    @Override
    public void onTabClose() {
        this.gui.closeCurrentTab();

        if (tabbedPane.getSelectedIndex() == 0)
            newWelcomeTab();
    }
}
