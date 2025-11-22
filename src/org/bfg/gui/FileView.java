package org.bfg.gui;

import org.bfg.Context;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.gui.custom.ImageView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

final class FileView extends JPanel {

    private final Context context;
    private final JComboBox<String> nameSelection;
    private final JComboBox<String> styleSelection;
    private final JSpinner sizeSelection;
    private final JSpinner countSelection;
    private final JCheckBox antiAlias;

    private final ImageView imageView;
    private String lastName;
    private String lastStyle;
    private int lastSize;
    private int lastCount;
    private boolean lastAntiAlias;

    FileView(Context context) {
        super();
        setLayout(new BorderLayout());
        this.context = context;

        // ---------------------------------------

        final JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.LINE_AXIS));

        this.nameSelection = new JComboBox<>(getAllFontNames());
        this.nameSelection.addActionListener(actionEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Font: "));
        topBar.add(this.nameSelection);

        this.styleSelection = new JComboBox<>(
            new String[] {"Plain", "Bold", "Italic"});
        this.styleSelection.addActionListener(actionEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Style: "));
        topBar.add(this.styleSelection);

        this.sizeSelection = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        this.sizeSelection.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Size: "));
        topBar.add(this.sizeSelection);

        this.countSelection = new JSpinner(new SpinnerNumberModel(256, 1, 1024, 1));
        countSelection.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Count: "));
        topBar.add(countSelection);

        this.antiAlias = new JCheckBox();
        this.antiAlias.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(new JLabel(" Anti-Alias: "));
        topBar.add(this.antiAlias);

        add(topBar, BorderLayout.PAGE_START);

        // ---------------------------------------

        this.imageView = new ImageView();
        add(this.imageView, BorderLayout.CENTER);

        this.lastName = "";
        this.lastStyle = "";
        this.lastSize = 0;
        this.lastCount = 0;
        this.lastAntiAlias = false;
        generateFont();
    }

    private void generateFont() {
        final String name = (String) this.nameSelection.getSelectedItem();
        final String style = (String) this.styleSelection.getSelectedItem();
        final int size = (Integer) this.sizeSelection.getValue();
        final int count = (Integer) this.countSelection.getValue();
        final boolean antiAlias = this.antiAlias.isSelected();

        if (name == null || style == null)
            return;

        if (name.equals(this.lastName) && style.equals(this.lastStyle) && size == this.lastSize &&
            count == this.lastCount && antiAlias == this.lastAntiAlias)
            return;

        this.lastName = name;
        this.lastStyle = style;
        this.lastSize = size;
        this.lastCount = count;
        this.lastAntiAlias = antiAlias;

        final Font font = new Font(
            name,
            getStyleId(style),
            size
        );

        final BitmapFontGenerator bitmapFontGenerator = new BitmapFontGenerator(font, (char) count,
            antiAlias);
        bitmapFontGenerator.generateAll();

        final BufferedImage image = bitmapFontGenerator.getImage();
        this.imageView.setImage(image);
        bitmapFontGenerator.dispose();

        this.imageView.invalidate();
        this.imageView.repaint();

        final int width = image.getWidth();
        final int height = image.getHeight();
        this.context.renameCurrentTab(name + " (" + width + "x" + height + ")");
    }

    private static String[] getAllFontNames() {
        final GraphicsEnvironment graphicsEnvironment =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        final Font[] fonts = graphicsEnvironment.getAllFonts();
        final String[] names = new String[fonts.length];

        for (int index = 0; index < fonts.length; index++)
            names[index] = fonts[index].getFontName();

        return names;
    }

    private static int getStyleId(String name) {
        return switch (name.toLowerCase()) {
            case "plain" -> Font.PLAIN;
            case "italic" -> Font.ITALIC;
            case "bold" -> Font.BOLD;
            default -> throw new IllegalArgumentException("Style unknown: '" + name + "'");
        };
    }
}
