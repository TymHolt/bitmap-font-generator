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
}
