package org.bfg;

import org.bfg.cli.MainCli;
import org.bfg.gui.MainGui;
import org.bfg.gui.MainGuiPresenter;

import javax.swing.*;

/**
 * Main class of the runnable application.
 */
public final class Main {

    private static boolean mainCalled = false;

    /**
     * Application main method. Runs in GUI mode when no arguments are given. With arguments the CLi mode will be used.
     *
     * @param args Command line arguments passed by the system.
     */
    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");
        mainCalled = true;

        if (args.length > 0)
            runCli(args);
        else
            runGui();
    }

    private static void runCli(String[] args) {
        try {
            MainCli.run(args);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static void runGui() {
        try {
            final MainGui gui = new MainGui();
            final MainGuiPresenter guiPresenter = new MainGuiPresenter(gui);
            gui.setPresenter(guiPresenter);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }
}
