package org.bfg.gui.tabs.file.dialog;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
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
        this.dataFileField = new JTextField();

        final JButton imageFileSelectButon = new JButton("...");
        imageFileSelectButon.addActionListener(_ -> {
            final String path = showFileChooser("Choose image file", ".png", "Image (*.png)");
            if (path == null)
                return;

            this.imageFileField.setText(path);

            if (this.dataFileField.getText().isEmpty())
                this.dataFileField.setText(changeFileExtension(path, "xml"));
        });
        addRow("Image File", null, this.imageFileField, imageFileSelectButon);

        add(Box.createVerticalStrut(50));

        final JButton dataFileSelectButon = new JButton("...");
        dataFileSelectButon.addActionListener(_ -> {
            final String path = showFileChooser("Choose data file", ".xml", "Data (*.xml)");
            if (path == null)
                return;

            this.dataFileField.setText(path);

            if (this.imageFileField.getText().isEmpty())
                this.imageFileField.setText(changeFileExtension(path, "xml"));
        });
        addRow("Data File", null, this.dataFileField, dataFileSelectButon);

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
        addRow(cancelButton, Box.createHorizontalStrut(360), exportButton);

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

    private String showFileChooser(String title, String extension, String description) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(extension) || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return description;
            }
        });
        fileChooser.setAcceptAllFileFilterUsed(false);

        final int action = fileChooser.showDialog(this, title);
        if (action != JFileChooser.APPROVE_OPTION)
            return null;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(extension))
            path = path + extension;

        return path;
    }

    private static String changeFileExtension(String path, String newExtension) {
        int extensionIndex;
        for(extensionIndex = path.length() - 1; extensionIndex >= 0; extensionIndex--) {
            if (path.charAt(extensionIndex) == '.')
                break;
        }

        return path.substring(0, extensionIndex + 1) + newExtension;
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
