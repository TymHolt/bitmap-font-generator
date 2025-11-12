package org.bfg.gui;

import org.bfg.Context;

import javax.swing.*;
import java.awt.*;

public final class MainGui extends JFrame {

    private final JTabbedPane tabbedPane;

    public MainGui(Context context) {
        super("Bitmap Font Generator");
        setLayout(new BorderLayout());

        final JMenuBar menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem newItem = new JMenuItem("New...");
        newItem.addActionListener(actionEvent -> {
            context.actionNewFile();
        });
        fileMenu.add(newItem);

        final JMenuItem exportItem = new JMenuItem("Export...");
        exportItem.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(this, "Export not implemented yet", "Info",
                JOptionPane.INFORMATION_MESSAGE);
        });
        fileMenu.add(exportItem);

        menuBar.add(fileMenu);
        add(menuBar, BorderLayout.PAGE_START);

        // ---------------------------------------

        this.tabbedPane = new JTabbedPane();
        final WelcomeView welcomeTab = new WelcomeView(context);
        openTab("Welcome", welcomeTab);

        add(this.tabbedPane, BorderLayout.CENTER);

        // ---------------------------------------

        final Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();;
        windowSize.width /= 2;
        windowSize.height /= 2;
        setSize(windowSize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openTab(String title, Component component) {
        for (int tabIndex = 0; tabIndex < this.tabbedPane.getTabCount(); tabIndex++) {
            if (this.tabbedPane.getComponentAt(tabIndex) instanceof WelcomeView) {
                this.tabbedPane.remove(tabIndex);
                break;
            }
        }

        this.tabbedPane.addTab(title, component);
        this.tabbedPane.setSelectedComponent(component);
    }

    public void newFileTab(String title) {
        openTab(title, new FileView());
    }
}
