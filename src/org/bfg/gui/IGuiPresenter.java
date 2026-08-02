package org.bfg.gui;

import org.bfg.gui.tabs.TabView;

public interface IGuiPresenter {
    void onOpenNewFile();
    void onRenameTab(String title);
    void onTabClose(TabView view);
}
