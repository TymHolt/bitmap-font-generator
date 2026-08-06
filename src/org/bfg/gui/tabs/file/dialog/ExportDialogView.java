package org.bfg.gui.tabs.file.dialog;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.Objects;

public final class ExportDialogView extends JDialog {

    public interface IExportDialogViewPresenter {

    };
    private IExportDialogViewPresenter presenter = new IExportDialogViewPresenter() {

    };

    private final JTextField imageFileField;
    private final JTextField dataFileField;
    private boolean confirmed = false;

    public ExportDialogView(JFrame parent, String title) {
        super(parent, title, true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        this.imageFileField = new JTextField();
        addRow("Image File", null, this.imageFileField, new JButton("..."));

        add(Box.createVerticalStrut(50));

        this.dataFileField = new JTextField();
        addRow("Data File", null, this.dataFileField, new JButton("..."));

        add(Box.createVerticalStrut(50));

        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(_ -> {
            this.confirmed = false;
            dispose();
        });
        final JButton exportButton = new JButton("Export");
        exportButton.addActionListener(_ -> {
            this.confirmed = true;
            dispose();
        });
        addRow(cancelButton, Box.createHorizontalStrut(50), exportButton);

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void addRow(Component left, Component center, Component right) {
        addRow(null, left, center, right);
    }

    private void addRow(String label, Component left, Component center, Component right) {
        if (label != null) {
            final JPanel labelContainer = new JPanel(new BorderLayout());
            final JLabel labelComponent = new JLabel(label);
            labelComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
            labelContainer.add(labelComponent, BorderLayout.CENTER);
            add(labelContainer);
        }

        final JPanel row = new JPanel(new BorderLayout());

        if (left != null)
            row.add(left, BorderLayout.LINE_START);

        if (center != null)
            row.add(center, BorderLayout.CENTER);

        if (right != null)
            row.add(right, BorderLayout.LINE_END);

        add(row);
    }

    public void setPresenter(IExportDialogViewPresenter presenter) {
        Objects.requireNonNull(presenter);
        this.presenter = presenter;
    }

    public ExportDialogResult getResult() {
        return new ExportDialogResult(
            this.confirmed, new File(this.imageFileField.getText()), new File(this.dataFileField.getText()));
    }

    public static class ExportDialogResult {

        public final boolean confirmed;
        public final File imageFile;
        public final File dataFile;

        private ExportDialogResult(boolean confirmed, File imageFile, File dataFile) {
            this.confirmed = confirmed;
            this.imageFile = imageFile;
            this.dataFile = dataFile;
        }
    };
}
