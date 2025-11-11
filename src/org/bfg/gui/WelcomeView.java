package org.bfg.gui;

import org.bfg.Context;
import org.bfg.gui.custom.LinkLabel;

import javax.swing.*;
import java.awt.*;

final class WelcomeView extends JPanel {

    WelcomeView(Context context) {
        super();
        setLayout(new BorderLayout());

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
            context.actionNewFile();
        });
        innerContainer.add(newFileLabel);

        outerContainer.add(innerContainer, defaultConstraints);

        // ---------------------------------------

        add(outerContainer, BorderLayout.CENTER);
    }
}
