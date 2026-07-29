package org.bfg;

import org.bfg.gui.MainGui;

public final class Context {

    private final MainGui gui;

    Context() {
        this.gui = new MainGui(this);
    }

    public void actionNewFile() {
        this.gui.newFileTab("New");
    }

    public void renameCurrentTab(String title) {
        this.gui.renameCurrentTab(title);
    }

    public void closeCurrentTab() {
        this.gui.closeCurrentTab();

        if (this.gui.getTabCount() == 0)
            this.gui.newWelcomeTab();
    }

    public boolean shouldShowGrid() {
        return this.gui.shouldShowGrid();
    }
}
