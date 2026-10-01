package org.bfg.gui;

import org.bfg.gui.tabs.TabView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * The main window of this application, with tabs and menus.
 */
public final class MainGui extends JFrame {

    /**
     * The presenter interface for this GUI.
     */
    public interface IMainGuiPresenter extends IGuiPresenter {
        /**
         * Will be called by this GUI when the show grid action is toggled.
         *
         * @param state The new state of the show grid flag.
         */
        void onActionShowGrid(boolean state);

        /**
         * Will be called by this GUI when the show UV coordinates action is toggled.
         *
         * @param state The new state of the show UV coordinates flag.
         */
        void onActionShowUvCoordinates(boolean state);
    }
    private IMainGuiPresenter guiPresenter = new IMainGuiPresenter() {
        @Override
        public void onActionShowGrid(boolean state) {}
        @Override
        public void onActionShowUvCoordinates(boolean state) {}
        @Override
        public void onTabClose(TabView view) {}
        @Override
        public JFrame getGuiParent() {return null;}
        @Override
        public void onOpenNewFile() {}
        @Override
        public void onRenameTab(TabView view, String title) {}
    };
    private final JTabbedPane tabbedPane;

    /**
     * Creates the visible main window with no presenter set. The presenter should be given this instance to set itself.
     */
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
        newItem.addActionListener(event -> this.guiPresenter.onOpenNewFile());
        fileMenu.add(newItem);

        final JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(event -> {
            final TabView openedTab = getCurrenTab();
            if (openedTab != null)
                openedTab.getPresenter().doActionExport();
        });
        fileMenu.add(exportItem);

        final JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(event -> this.closeTab(this.getCurrenTab()));
        fileMenu.add(closeItem);

        return fileMenu;
    }

    private JMenu createViewMenu() {
        final JMenu viewMenu = new JMenu("View");

        final JCheckBoxMenuItem showGridItem = new JCheckBoxMenuItem("Show Grid");
        showGridItem.setState(false);
        showGridItem.addItemListener(event -> this.guiPresenter.onActionShowGrid(showGridItem.isSelected()));
        viewMenu.add(showGridItem);

        final JCheckBoxMenuItem showUvCoordinatesItem = new JCheckBoxMenuItem("Show UV-Coordinates");
        showUvCoordinatesItem.setState(false);
        showUvCoordinatesItem.addItemListener(event ->
                this.guiPresenter.onActionShowUvCoordinates(showUvCoordinatesItem.isSelected()));
        viewMenu.add(showUvCoordinatesItem);

        return viewMenu;
    }

    /**
     * Sets the presenter for this GUI.
     *
     * @param presenter The presenter, must not be {@code null}.
     */
    public void setPresenter(IMainGuiPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.guiPresenter = presenter;
    }

    /**
     * Opens the given {@link TabView} with the given title.
     *
     * @param title The title, may be {@code null}.
     * @param view  The tab, must not be {@code null}.
     */
    public void openTab(String title, TabView view) {
        Objects.requireNonNull(view);
        this.tabbedPane.addTab(title, view);
        this.tabbedPane.setSelectedComponent(view);
        setTabTitle(view, title);
        view.getPresenter().onAfterOpen();
    }

    /**
     * @return The current visible {@link TabView}, or {@code null} if none is currently visible.
     */
    public TabView getCurrenTab() {
        return (TabView) this.tabbedPane.getSelectedComponent();
    }

    /**
     * Returns the {@link TabView} that is at the position of the given index.
     *
     * @param index The index of the tab.
     * @return The {@link TabView} at the {@code index}.
     */
    public TabView getTabAt(int index) {
        return (TabView) this.tabbedPane.getComponentAt(index);
    }

    /**
     * Updates the title of the given {@link TabView}.
     *
     * @param view  The tab to update the title of, must not be {@code null}.
     * @param title The new title, may be {@code null}.
     */
    public void setTabTitle(TabView view, String title) {
        Objects.requireNonNull(view);
        this.tabbedPane.setTabComponentAt(getTabIndex(view), createTabTitle(title));
    }

    /**
     * Removes the given tab.
     *
     * @param view The tab close, must not be {@code null}.
     */
    public void closeTab(TabView view) {
        Objects.requireNonNull(view);
        this.tabbedPane.remove(getTabIndex(view));
    }

    /**
     * @return The number of opened tabs.
     */
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
