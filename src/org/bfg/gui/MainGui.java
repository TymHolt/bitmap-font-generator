package org.bfg.gui;

import org.bfg.gui.tabs.TabView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public final class MainGui extends JFrame {

    public interface IMainGuiPresenter extends IGuiPresenter {
        void onActionShowGrid(boolean state);
    }
    private IMainGuiPresenter guiPresenter = new IMainGuiPresenter() {
        @Override
        public void onActionShowGrid(boolean state) {}
        @Override
        public void onTabClose(TabView view) {}
        @Override
        public JFrame getGuiParent() {return null;}
        @Override
        public void onOpenNewFile() {}
        @Override
        public void onRenameTab(String title) {}
    };
    private final JTabbedPane tabbedPane;

    public MainGui() {
        super("Bitmap Font Generator");
        setLayout(new BorderLayout());

        this.tabbedPane = new JTabbedPane();
        add(this.tabbedPane, BorderLayout.CENTER);

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());
        add(menuBar, BorderLayout.PAGE_START);

        final Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(windowSize.width / 2, windowSize.height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuItem createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(_ -> this.guiPresenter.onOpenNewFile());
        fileMenu.add(newItem);

        final JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(_ -> {
            final TabView openedTab = getCurrenTab();
            if (openedTab != null)
                openedTab.getPresenter().doActionExport();
        });
        fileMenu.add(exportItem);

        final JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(_ -> this.closeTab(this.getCurrenTab()));
        fileMenu.add(closeItem);

        return fileMenu;
    }

    private JMenu createViewMenu() {
        final JMenu viewMenu = new JMenu("View");

        final JCheckBoxMenuItem showGridItem = new JCheckBoxMenuItem("Show Grid");
        showGridItem.setState(false);
        showGridItem.addItemListener(_ -> this.guiPresenter.onActionShowGrid(showGridItem.isSelected()));
        viewMenu.add(showGridItem);

        return viewMenu;
    }

    public void setPresenter(IMainGuiPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.guiPresenter = presenter;
    }

    public void openTab(String title, TabView view) {
        Objects.requireNonNull(view);
        this.tabbedPane.addTab(title, view);
        this.tabbedPane.setSelectedComponent(view);
        setTabTitle(view, title);
    }

    public TabView getCurrenTab() {
        return (TabView) this.tabbedPane.getSelectedComponent();
    }

    public TabView getTabAt(int index) {
        return (TabView) this.tabbedPane.getComponentAt(index);
    }

    public void setTabTitle(TabView view, String title) {
        Objects.requireNonNull(view);
        this.tabbedPane.setTabComponentAt(getTabIndex(view), createTabTitle(title));
    }

    public void closeTab(TabView view) {
        Objects.requireNonNull(view);
        this.tabbedPane.remove(getTabIndex(view));
    }

    public int getTabCount() {
        return this.tabbedPane.getTabCount();
    }

    private int getTabIndex(TabView view) {
        for (int index = 0; index < this.tabbedPane.getTabCount(); index++)
            if (this.tabbedPane.getComponentAt(index) == view)
                return index;
        return -1;
    }

    private Component createTabTitle(String title) {
        if (title == null)
            title = "null";

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
                guiPresenter.onTabClose(getCurrenTab());
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
