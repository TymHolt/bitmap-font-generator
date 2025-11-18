package org.bfg.gui;

import org.bfg.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public final class MainGui extends JFrame {

    private final Context context;
    private final JTabbedPane tabbedPane;

    public MainGui(Context context) {
        super("Bitmap Font Generator");
        setLayout(new BorderLayout());
        this.context = context;

        // ---------------------------------------

        final JMenuBar menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(actionEvent -> {
            this.context.actionNewFile();
        });
        fileMenu.add(newItem);

        final JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(this, "Export not implemented yet", "Info",
                JOptionPane.INFORMATION_MESSAGE);
        });
        fileMenu.add(exportItem);

        final JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(actionEvent -> {
            this.context.closeCurrentTab();
        });
        fileMenu.add(closeItem);

        menuBar.add(fileMenu);
        add(menuBar, BorderLayout.PAGE_START);

        // ---------------------------------------

        this.tabbedPane = new JTabbedPane();
        final WelcomeView welcomeTab = new WelcomeView(this.context);
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
        final Component tabComponent = createTabComponent(title);
        this.tabbedPane.setTabComponentAt(this.tabbedPane.getSelectedIndex(), tabComponent);
    }

    public void newFileTab(String title) {
        if (title == null)
            title = "null";

        openTab(title, new FileView(this.context));
    }

    public void renameCurrentTab(String title) {
        if (title == null)
            title = "null";

        final int selectedIndex = this.tabbedPane.getSelectedIndex();
        if (selectedIndex < 0)
            return;

        this.tabbedPane.setTabComponentAt(selectedIndex, createTabComponent(title));
    }

    public void closeCurrentTab() {
        final int selectedIndex = this.tabbedPane.getSelectedIndex();
        if (selectedIndex < 0)
            return;

        this.tabbedPane.remove(selectedIndex);
    }

    private Component createTabComponent(String title) {
        final JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.LINE_AXIS));
        tabPanel.setOpaque(false);

        // ---------------------------------------

        final JLabel titleLabel = new JLabel(title);
        tabPanel.add(titleLabel);

        // ---------------------------------------

        tabPanel.add(Box.createHorizontalStrut(10));

        // ---------------------------------------

        final JLabel closeLabel = new JLabel("X");
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                final int index = tabbedPane.indexOfTabComponent(tabPanel);
                if (index < 0)
                    return;

                tabbedPane.remove(index);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        tabPanel.add(closeLabel);

        return tabPanel;
    }
}
