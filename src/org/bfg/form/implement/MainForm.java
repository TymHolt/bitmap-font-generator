package org.bfg.form.implement;

import org.bfg.form.base.InputForm;
import org.bfg.generate.BitmapGenerationRunnable;
import org.bfg.util.work.Work;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class MainForm extends InputForm {

    private JTextField fieldFontName = new JTextField();
    private JComboBox<String> fieldFontStyle = new JComboBox<>(new String[] {"Plain", "Bold", "Italic"});
    private JSpinner fieldFontSize = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
    private JButton buttonPreview = new JButton("Preview");
    private JButton buttonGenerate = new JButton("Generate");
    private BufferedImage preview;

    @Override
    protected void initInputs() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.fieldFontName = this.addTextInput("Font Name");
        this.fieldFontStyle = this.addSelectionInput("Font Style",
            new String[] {"Plain", "Bold", "Italic"});
        this.fieldFontSize = this.addNumberInput("Font Size", 10, 1, 100);
        this.buttonPreview = this.addAction("Preview", actionEvent -> {
            System.out.println("Preview");
        });
        this.buttonGenerate = this.addAction("Generate", actionEvent -> {
            final Font font = new Font(
                fieldFontName.getText(),
                getStyleId((String) this.fieldFontStyle.getSelectedItem()),
                (Integer) this.fieldFontSize.getValue()
            );

            final int charCount = 256;
            final BitmapGenerationRunnable bitmapGenerationRunnable = new BitmapGenerationRunnable(font, charCount);
            final Work work = new Work(bitmapGenerationRunnable, charCount);
            work.addWorkFinishListener(() -> {
                PreviewForm form = new PreviewForm();
                form.setPreview(bitmapGenerationRunnable.getResult());
                bitmapGenerationRunnable.dispose();
            });

            new WorkForm(work);
            work.executeAsync();

            this.update();
        });
    }

    @Override
    public void onUpdate() {
        SwingUtilities.invokeLater(() -> buttonPreview.setEnabled(this.preview != null));
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
