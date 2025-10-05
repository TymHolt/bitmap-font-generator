package org.bfg.form.implement;

import org.bfg.form.base.InputForm;
import org.bfg.generate.BitmapGenerationRunnable;
import org.bfg.generate.MetaDataGenerator;
import org.bfg.util.work.Work;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class MainForm extends InputForm {

    private JComboBox<String> fieldFontName;
    private JComboBox<String> fieldFontStyle;
    private JSpinner fieldFontSize;
    private JButton buttonPreview;
    private JButton buttonExport;
    private BufferedImage preview;
    private MetaDataGenerator metaData;

    @Override
    protected void initInputs() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this.fieldFontName = this.addTextInput("Font Name");
        this.fieldFontName = this.addSelectionInput("Font Name", getAllFontNames());
        this.fieldFontStyle = this.addSelectionInput("Font Style",
            new String[] {"Plain", "Bold", "Italic"});
        this.fieldFontSize = this.addNumberInput("Font Size", 10, 1, 100);
        this.buttonExport = this.addAction("Export", actionEvent -> {
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
            final int action = fileChooser.showDialog(this, "Export Image");

            if (action == JFileChooser.APPROVE_OPTION) {
                File imageFile = fileChooser.getSelectedFile();

                if (!imageFile.getAbsolutePath().endsWith(".png"))
                    imageFile = new File(imageFile.getAbsolutePath() + ".png");

                final String metaDataFilePath = changeFileExtension(imageFile.getAbsolutePath(), "txt");
                final File metaDataFile = new File(metaDataFilePath);

                try {
                    ImageIO.write(this.preview, "PNG", imageFile);
                    this.metaData.write(metaDataFile);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.buttonPreview = this.addAction("Preview", actionEvent -> {
            final PreviewForm form = new PreviewForm();
            form.setPreview(this.preview);
        });
        this.addAction("Generate", actionEvent -> {
            final Font font = new Font(
                (String) this.fieldFontName.getSelectedItem(),
                getStyleId((String) this.fieldFontStyle.getSelectedItem()),
                (Integer) this.fieldFontSize.getValue()
            );

            final char charCount = 256;
            final BitmapGenerationRunnable bitmapGenerationRunnable = new BitmapGenerationRunnable(font, charCount);
            final Work work = new Work(bitmapGenerationRunnable, charCount);
            work.addWorkFinishListener(() -> {
                this.preview = bitmapGenerationRunnable.getResultImage();
                this.metaData = bitmapGenerationRunnable.getResultMetaData();
                bitmapGenerationRunnable.dispose();
                this.update();
            });

            new WorkForm(work);
            work.executeAsync();
        });
    }

    @Override
    public void onUpdate() {
        final boolean previewGenerated = this.preview != null;
        this.buttonPreview.setEnabled(previewGenerated);
        this.buttonExport.setEnabled(previewGenerated);
    }

    private static String[] getAllFontNames() {
        final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
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

    private static String changeFileExtension(String path, String newExtension) {
        int extensionIndex = path.length() - 1;

        for(; extensionIndex >= 0; extensionIndex--) {
            if (path.charAt(extensionIndex) == '.')
                break;
        }

        return path.substring(0, extensionIndex + 1) + newExtension;
    }
}
