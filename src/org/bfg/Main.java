package org.bfg;

import javax.swing.*;

public final class Main {

    private static boolean mainCalled = false;

    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");
        mainCalled = true;

        try {
            new Context();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
