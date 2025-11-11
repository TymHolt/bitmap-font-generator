package org.bfg.gui;

import javax.swing.*;
import java.awt.*;

final class FileView extends JPanel {

    FileView() {
        super();
        setLayout(new BorderLayout());
        add(new JLabel("Test"), BorderLayout.CENTER);
    }
}
