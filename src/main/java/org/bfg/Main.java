package org.bfg;

import org.bfg.cli.ArgumentType;
import org.bfg.cli.CliArguments;
import org.bfg.cli.MainCli;
import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.Export;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphRange;
import org.bfg.gui.MainGui;
import org.bfg.gui.MainGuiPresenter;

import javax.swing.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Main class of the runnable application.
 */
public final class Main {

    private static boolean mainCalled = false;

    /**
     * Application main method.
     *
     * @param args Command line arguments passed by the system.
     */
    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");
        mainCalled = true;

        if (args.length > 0) {
            final MainCli cli = new MainCli();
            cli.parseArguments(args);
            cli.run();
        } else {
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

    private static void cliMode(String[] args) {
        String name = "";
        String style = "plain";
        int size = 20;
        int count = 256;
        boolean antiAlias = false;
        String outPath = "";

        final CliArguments arguments;
        try {
            // TODO Verify data (negative integers and style) + Optional arguments
            arguments = CliArguments.parse(args);
            name = arguments.parseSingleStringValue(ArgumentType.FONT);
            style = arguments.parseSingleStringValue(ArgumentType.STYLE);
            size = arguments.parseSingleIntegerValue(ArgumentType.SIZE);
            count = arguments.parseSingleIntegerValue(ArgumentType.COUNT);
            outPath = arguments.parseSingleStringValue(ArgumentType.OUT);
            antiAlias = arguments.parseSingleBooleanValue(ArgumentType.ANTIALIAS);
        } catch (CliArguments.CliParseException exception) {
            System.err.println(exception.getMessage());
            return;
        }

        final Font font = FontStyle.newFontWithStyle(name, style, size);
        final GlyphRange range = new GlyphRange((char) count);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias, false);
        try {
            // TODO Ask if overwrite
            Export.export(new File(outPath), new File(outPath + ".png"), bitmapFont, false);
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    /*private static void printUsage() {
        System.out.println("Usage (Required):");
        System.out.print("java -jar <path-to-jar>");
        for (ArgumentType requiredType : ArgumentType.values()) {
            if (!requiredType.required)
                continue;

            System.out.print(" " +  requiredType.longForm + " " + requiredType.parameterName);
        }
        System.out.println("\n\n");

        System.out.println("All options:");
        for (ArgumentType argumentType : ArgumentType.values()) {
            System.out.printf("%s (%s) %s - %s\n", argumentType.longForm,
                argumentType.shortForm, argumentType.parameterName, argumentType.description);
        }
    }*/
}
