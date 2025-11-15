package org.bfg;

import org.bfg.gui.MainGui;

public final class Context {

    private final MainGui gui;

    Context() {
        this.gui = new MainGui(this);
    }

    public void actionNewFile() {
        //new MainForm();
        this.gui.newFileTab("New");
    }
}
