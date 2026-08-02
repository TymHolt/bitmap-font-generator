package org.bfg.gui;

import org.bfg.gui.tabs.TabView;
import org.bfg.gui.tabs.file.FileTabPresenter;
import org.bfg.gui.tabs.file.FileTabView;
import org.bfg.gui.tabs.welcome.WelcomeTabPresenter;
import org.bfg.gui.tabs.welcome.WelcomeTabView;

import java.util.Objects;

public final class MainGuiPresenter implements MainGui.IMainGuiPresenter {

    private final MainGui gui;

    public MainGuiPresenter(MainGui gui) {
        Objects.requireNonNull(gui);
        this.gui = gui;
    }

    @Override
    public void onOpenNewFile() {
        final FileTabView view = new FileTabView();
        final FileTabPresenter presenter = new FileTabPresenter(view, this);
        view.setPresenter(presenter);
        this.gui.openTab("New", view);
    }

    @Override
    public void onRenameTab(String title) {
        this.gui.setTabTitle(this.gui.getCurrenTab(), title);
    }

    @Override
    public void onTabClose(TabView view) {
        this.gui.closeTab(view);

        if (this.gui.getTabCount() == 0) {
            final WelcomeTabView welcomeTabView = new WelcomeTabView();
            final WelcomeTabPresenter presenter = new WelcomeTabPresenter(welcomeTabView, this);
            welcomeTabView.setPresenter(presenter);
            this.gui.openTab("Welcome", welcomeTabView);
        }
    }
}
