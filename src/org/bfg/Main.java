package org.bfg;

import org.bfg.form.implement.MainForm;

public final class Main {

    private static boolean mainCalled = false;

    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");

        mainCalled = true;

        try {
            new MainForm();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
