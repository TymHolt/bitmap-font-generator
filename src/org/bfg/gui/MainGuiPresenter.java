package org.bfg.gui;

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
        final FileTabPresenter presenter = new FileTabPresenter(view);
        view.setPresenter(presenter);
        this.gui.openTab("New", view);
        System.out.println("wwd");
    }

    @Override
    public void onTabClose() {
        this.gui.closeCurrentTab();

        if (this.gui.getTabCount() != 0)
            return;

        final WelcomeTabView view = new WelcomeTabView();
        final WelcomeTabPresenter presenter = new WelcomeTabPresenter(view, this);
        view.setPresenter(presenter);
        this.gui.openTab("Welcome", view);
    }
}
