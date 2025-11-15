package org.bfg.gui;

import org.bfg.generate.BitmapFontGenerator;
import org.bfg.gui.custom.ImageView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class FileView extends JPanel {

    private final JComboBox<String> nameSelection;
    private final JComboBox<String> styleSelection;
    private final JSpinner sizeSelection;
    private final ImageView imageView;

    FileView() {
        super();
        setLayout(new BorderLayout());

        // ---------------------------------------

        final JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.LINE_AXIS));

        this.nameSelection = new JComboBox<>(getAllFontNames());
        nameSelection.addActionListener(actionEvent -> {
            // TODO This gets called twice
            generateFont();
        });
        topBar.add(nameSelection);

        this.styleSelection = new JComboBox<>(
            new String[] {"Plain", "Bold", "Italic"});
        styleSelection.addActionListener(actionEvent -> {
            // TODO This gets called twice
            generateFont();
        });
        topBar.add(styleSelection);

        this.sizeSelection = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        sizeSelection.addChangeListener(changeEvent -> {
            generateFont();
        });
        topBar.add(sizeSelection);

        add(topBar, BorderLayout.PAGE_START);

        // ---------------------------------------

        this.imageView = new ImageView();
        add(this.imageView, BorderLayout.CENTER);

        generateFont();
    }

    private void generateFont() {
        final Font font = new Font(
            (String) this.nameSelection.getSelectedItem(),
            getStyleId((String) this.styleSelection.getSelectedItem()),
            (Integer) this.sizeSelection.getValue()
        );

        final BitmapFontGenerator bitmapFontGenerator = new BitmapFontGenerator(font, (char) 256);
        bitmapFontGenerator.generateAll();

        this.imageView.setImage(bitmapFontGenerator.getImage());
        bitmapFontGenerator.dispose();

        this.imageView.invalidate();
        this.imageView.repaint();
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
