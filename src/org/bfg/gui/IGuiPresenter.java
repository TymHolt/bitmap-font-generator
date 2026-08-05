package org.bfg.gui;

import org.bfg.gui.tabs.TabView;

import javax.swing.JFrame;

public interface IGuiPresenter {
    void onOpenNewFile();
    void onRenameTab(String title);
    void onTabClose(TabView view);
    JFrame getGuiParent(); // TODO This can be cleaner...
}
