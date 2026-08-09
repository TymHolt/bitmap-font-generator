package org.bfg.gui.tabs.welcome;

import org.bfg.gui.custom.LinkLabel;
import org.bfg.gui.tabs.TabView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class WelcomeTabView extends TabView {

    public interface IWelcomeTabPresenter extends ITabPresenter {
        void onOpenNewFile();
    }
    private IWelcomeTabPresenter presenter = new IWelcomeTabPresenter() {
        @Override
        public void onOpenNewFile() {}
        @Override
        public void doActionExport() {}
        @Override
        public void setShowGrid(boolean flag) {}
    };

    public WelcomeTabView() {
        super(new BorderLayout());
        final JPanel container = addCenteredContainer();

        final JLabel welcomeLabel = new JLabel("Welcome, start by");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(welcomeLabel);

        final LinkLabel newFileLabel = new LinkLabel("creating a new bitmap font...");
        newFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newFileLabel.setOnClick(() -> this.presenter.onOpenNewFile());
        container.add(newFileLabel);
    }

    private JPanel addCenteredContainer() {
        final JPanel outerContainer = new JPanel();
        outerContainer.setLayout(new GridBagLayout());

        final JPanel innerContainer = new JPanel();
        innerContainer.setLayout(new BoxLayout(innerContainer, BoxLayout.PAGE_AXIS));
        outerContainer.add(innerContainer, new GridBagConstraints());

        add(outerContainer, BorderLayout.CENTER);
        return innerContainer;
    }

    public void setPresenter(IWelcomeTabPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.presenter = presenter;
    }

    @Override
    public ITabPresenter getPresenter() {
        return this.presenter;
    }
}
