package org.bfg.gui;

import org.bfg.gui.tabs.ITabView;
import org.bfg.gui.tabs.welcome.WelcomeTabView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public final class MainGui extends JFrame {

    private IMainGuiPresenter guiPresenter = new IMainGuiPresenter() {
        @Override
        public void onTabClose() {}
        @Override
        public void onOpenNewFile() {}
    };
    private final JTabbedPane tabbedPane;

    public MainGui() {
        super("Bitmap Font Generator");
        setLayout(new BorderLayout());

        this.tabbedPane = new JTabbedPane();
        add(this.tabbedPane, BorderLayout.CENTER);

        // ---------------------------------------

        final JMenuBar menuBar = new JMenuBar();

        // ---------------------------------------

        final JMenu fileMenu = new JMenu("File");

        final JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(actionEvent -> this.guiPresenter.onOpenNewFile());
        fileMenu.add(newItem);

        final JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(actionEvent -> {
            final ITabView openedTab = getOpenedFontView();
            if (openedTab != null)
                openedTab.getPresenter().doActionExport();
        });
        fileMenu.add(exportItem);

        final JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this::actionPerformed);
        fileMenu.add(closeItem);

        menuBar.add(fileMenu);

        // ---------------------------------------

        final JMenu viewMenu = new JMenu("View");

        final JCheckBoxMenuItem showGridItem = new JCheckBoxMenuItem("Show Grid");
        showGridItem.setState(false);
        showGridItem.addItemListener(itemEvent -> {
            // TODO Move to GuiPresenter
            for (int index = 0; index < this.tabbedPane.getTabCount(); index++) {
                final Component tab = this.tabbedPane.getComponentAt(index);
                if (tab instanceof ITabView)
                    ((ITabView) tab).getPresenter().setShowGrid(showGridItem.getState());
            }

            this.tabbedPane.invalidate();
            this.tabbedPane.repaint();
        });

        viewMenu.add(showGridItem);

        menuBar.add(viewMenu);

        // ---------------------------------------

        add(menuBar, BorderLayout.PAGE_START);

        // ---------------------------------------

        final Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();;
        windowSize.width /= 2;
        windowSize.height /= 2;
        setSize(windowSize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setPresenter(IMainGuiPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.guiPresenter = presenter;
    }

    public void openTab(String title, Component component) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(component);

        // TODO Move to GuiPresenter
        for (int tabIndex = 0; tabIndex < this.tabbedPane.getTabCount(); tabIndex++) {
            if (this.tabbedPane.getComponentAt(tabIndex) instanceof WelcomeTabView) {
                this.tabbedPane.remove(tabIndex);
                break;
            }
        }

        this.tabbedPane.addTab(title, component);
        this.tabbedPane.setSelectedComponent(component);
        final Component tabComponent = createTabComponent(title);
        this.tabbedPane.setTabComponentAt(this.tabbedPane.getSelectedIndex(), tabComponent);
    }

    // TODO
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

    public int getTabCount() {
        return this.tabbedPane.getTabCount();
    }

    private ITabView getOpenedFontView() {
        final Component tab = this.tabbedPane.getSelectedComponent();
        if (tab instanceof ITabView)
            return (ITabView) tab;

        return null;
    }

    private Component createTabComponent(String title) {
        final JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.LINE_AXIS));
        tabPanel.setOpaque(false);

        final JLabel titleLabel = new JLabel(title);
        tabPanel.add(titleLabel);

        tabPanel.add(Box.createHorizontalStrut(10));

        final JLabel closeLabel = new JLabel("X");
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                guiPresenter.onTabClose();
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

    private void actionPerformed(ActionEvent actionEvent) {
        closeCurrentTab();
    }

    public interface IMainGuiPresenter extends IGuiPresenter {
        void onTabClose();
    }
}
