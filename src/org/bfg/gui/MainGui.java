package org.bfg.gui;

import org.bfg.Context;
import org.bfg.generate.BitmapFont;
import org.bfg.generate.MetaDataGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public final class MainGui extends JFrame {

    private final Context context;
    private final JTabbedPane tabbedPane;
    private final JCheckBoxMenuItem showGridItem;

    public MainGui(Context context) {
        super("Bitmap Font Generator");
        setLayout(new BorderLayout());
        this.context = context;

        // ---------------------------------------

        this.tabbedPane = new JTabbedPane();
        final WelcomeView welcomeTab = new WelcomeView(this.context);
        openTab("Welcome", welcomeTab);

        add(this.tabbedPane, BorderLayout.CENTER);

        // ---------------------------------------

        final JMenuBar menuBar = new JMenuBar();

        // ---------------------------------------

        final JMenu fileMenu = new JMenu("File");

        final JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(actionEvent -> {
            this.context.actionNewFile();
        });
        fileMenu.add(newItem);

        final JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(actionEvent -> {
            final FileView openedTab = getOpenedFontView();
            if (openedTab == null)
                return;

            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith(".png") || file.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "Image (*.png)";
                }
            });
            fileChooser.setAcceptAllFileFilterUsed(false);

            final int action = fileChooser.showDialog(this, "Export font");
            if (action != JFileChooser.APPROVE_OPTION)
                return;

            File imageFile = fileChooser.getSelectedFile();
            if (!imageFile.getAbsolutePath().endsWith(".png"))
                imageFile = new File(imageFile.getAbsolutePath() + ".png");

            final String dataFilePath = changeFileExtension(imageFile.getAbsolutePath(), "xml");
            final File dataFile = new File(dataFilePath);

            try {
                final BitmapFont font = openedTab.getBitmapFont();
                ImageIO.write(font.getAtlasImage(), "PNG", imageFile);
                MetaDataGenerator.exportMetaData(dataFile, font);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        fileMenu.add(exportItem);

        final JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(actionEvent -> {
            this.context.closeCurrentTab();
        });
        fileMenu.add(closeItem);

        menuBar.add(fileMenu);

        // ---------------------------------------

        final JMenu viewMenu = new JMenu("View");

        this.showGridItem = new JCheckBoxMenuItem("Show Grid");
        this.showGridItem.setState(false);
        this.showGridItem.addItemListener(itemEvent -> {
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

    public boolean shouldShowGrid() {
        return this.showGridItem.getState();
    }

    private FileView getOpenedFontView() {
        final Component tab = this.tabbedPane.getSelectedComponent();
        if (tab instanceof FileView)
            return (FileView) tab;

        return null;
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

    private static String changeFileExtension(String path, String newExtension) {
        int extensionIndex = path.length() - 1;

        for(; extensionIndex >= 0; extensionIndex--) {
            if (path.charAt(extensionIndex) == '.')
                break;
        }

        return path.substring(0, extensionIndex + 1) + newExtension;
    }
}
