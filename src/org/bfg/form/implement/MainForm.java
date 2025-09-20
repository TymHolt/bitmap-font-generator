package org.bfg.form.implement;

import org.bfg.form.base.InputForm;
import org.bfg.generate.BitmapGenerationRunnable;
import org.bfg.util.work.Work;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class MainForm extends InputForm {

    private JTextField fieldFontName;
    private JComboBox<String> fieldFontStyle;
    private JSpinner fieldFontSize;
    private JButton buttonPreview;
    private JButton buttonExport;
    private BufferedImage preview;

    @Override
    protected void initInputs() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.fieldFontName = this.addTextInput("Font Name");
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
                final File file = fileChooser.getSelectedFile();

                try {
                    ImageIO.write(this.preview, "PNG", file);
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
                fieldFontName.getText(),
                getStyleId((String) this.fieldFontStyle.getSelectedItem()),
                (Integer) this.fieldFontSize.getValue()
            );

            final int charCount = 256;
            final BitmapGenerationRunnable bitmapGenerationRunnable = new BitmapGenerationRunnable(font, charCount);
            final Work work = new Work(bitmapGenerationRunnable, charCount);
            work.addWorkFinishListener(() -> {
                this.preview = bitmapGenerationRunnable.getResult();
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

    private static int getStyleId(String name) {
        return switch (name.toLowerCase()) {
            case "plain" -> Font.PLAIN;
            case "italic" -> Font.ITALIC;
            case "bold" -> Font.BOLD;
            default -> throw new IllegalArgumentException("Style unknown: '" + name + "'");
        };
    }
}
