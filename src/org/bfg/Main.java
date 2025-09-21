package org.bfg;

import org.bfg.form.implement.MainForm;

import javax.swing.*;

public final class Main {

    private static boolean mainCalled = false;

    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");

        mainCalled = true;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Info",
                JOptionPane.INFORMATION_MESSAGE);
        }

        try {
            new MainForm();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
