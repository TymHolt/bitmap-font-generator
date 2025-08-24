package org.bfg.form.implement;

import org.bfg.form.base.InputForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.fieldFontName = this.addTextInput("Font Name");
        this.fieldFontStyle = this.addSelectionInput("Font Style",
            new String[] {"Plain", "Bold", "Italic"});
        this.fieldFontSize = this.addNumberInput("Font Size", 10, 1, 100);
        this.buttonPreview = this.addAction("Preview", actionEvent -> {

        });
        this.buttonGenerate = this.addAction("Generate", actionEvent -> {

        });
    }

    @Override
    public void onUpdate() {
        this.buttonPreview.setEnabled(this.preview != null);
    }
}
