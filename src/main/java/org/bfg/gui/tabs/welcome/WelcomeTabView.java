package org.bfg.gui.tabs.welcome;

import org.bfg.gui.custom.LinkLabel;
import org.bfg.gui.tabs.TabView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A view component to be shown on application startup or when all tabs are closed. Gives the user directions to quickly
 * get started.
 */
public final class WelcomeTabView extends TabView {

    /**
     * The presenter interface for this view.
     */
    public interface IWelcomeTabPresenter extends ITabPresenter {
        /**
         * Will be called when the link for a new file was clicked.
         */
        void onOpenNewFile();
    }
    private IWelcomeTabPresenter presenter = new IWelcomeTabPresenter() {
        @Override
        public void onOpenNewFile() {}
        @Override
        public void doActionExport() {}
        @Override
        public void setShowGrid(boolean flag) {}
        @Override
        public void setShowUvCoordinates(boolean flag) {}
        @Override
        public void onAfterOpen() {}
    };

    /**
     * Builds the view with a placeholder presenter. The presenter should get this instance passed and set itself using
     * {@link #setPresenter(IWelcomeTabPresenter)}.
     */
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

    /**
     * Set the presenter for this view.
     *
     * @param presenter The presenter to use, must not be {@code null}.
     */
    public void setPresenter(IWelcomeTabPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.presenter = presenter;
    }

    @Override
    public ITabPresenter getPresenter() {
        return this.presenter;
    }
}
