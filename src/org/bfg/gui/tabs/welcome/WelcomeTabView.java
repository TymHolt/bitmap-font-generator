package org.bfg.gui.tabs.welcome;

import org.bfg.gui.custom.LinkLabel;
import org.bfg.gui.tabs.ITabPresenter;
import org.bfg.gui.tabs.ITabView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class WelcomeTabView extends JPanel implements ITabView {

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

        final JPanel outerContainer = new JPanel();
        outerContainer.setLayout(new GridBagLayout());
        final GridBagConstraints defaultConstraints = new GridBagConstraints();

        // ---------------------------------------

        final JPanel innerContainer = new JPanel();
        innerContainer.setLayout(new BoxLayout(innerContainer, BoxLayout.PAGE_AXIS));

        final JLabel welcomeLabel = new JLabel("Welcome, start by");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerContainer.add(welcomeLabel);

        final LinkLabel newFileLabel = new LinkLabel("creating a new bitmap font...");
        newFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newFileLabel.addActionListener(actionEvent -> {
            this.presenter.onOpenNewFile();
        });
        innerContainer.add(newFileLabel);

        outerContainer.add(innerContainer, defaultConstraints);

        // ---------------------------------------

        add(outerContainer, BorderLayout.CENTER);
    }

    public void setPresenter(IWelcomeTabPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.presenter = presenter;
    }

    @Override
    public ITabPresenter getPresenter() {
        return this.presenter;
    }

    public interface IWelcomeTabPresenter extends ITabPresenter {
        void onOpenNewFile();
    }
}
