package org.bfg.gui.tabs.file.font;

import java.awt.Rectangle;

public interface ICharSelectionCallback {

    void onSelectChar(char c, Rectangle bounds);
    void onClearSelection();
}
