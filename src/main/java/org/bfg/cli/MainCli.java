package org.bfg.cli;

import org.bfg.generate.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The handler for CLI mode.
 */
public final class MainCli {

    /**
     * Run the CLI mode with the system CLI arguments.
     *
     * @param args The CLI arguments passed by the system.
     */
    public static void run(String[] args) {
        String name;
        String style;
        int size;
        int count;
        boolean antiAlias;
        String outPath;

        final CliArguments arguments;
        try {
            arguments = CliArguments.parse(args);
            name = arguments.parseSingleStringValue(ArgumentType.FONT, "");
            style = arguments.parseSingleStringValue(ArgumentType.STYLE, "plain");
            size = arguments.parseSingleIntegerValue(ArgumentType.SIZE, 20);
            count = arguments.parseSingleIntegerValue(ArgumentType.COUNT, 255);
            outPath = arguments.parseSingleStringValue(ArgumentType.OUT, null);
            antiAlias = arguments.parseSingleBooleanValue(ArgumentType.ANTIALIAS, false);
        } catch (CliArguments.CliParseException exception) {
            System.err.println(exception.getMessage());
            return;
        }

        if (outPath == null) {
            System.err.println("No file path for export given");
            return;
        }

        if (!verifyPositiveNonZero(size, "Font size"))
            return;

        if (!verifyPositiveNonZero(count, "Count"))
            return;

        // TODO Hacky to test for valid font style
        try {
            FontStyle.newFontWithStyle(name, style, size);
        } catch (Exception exception) {
            System.err.println("Unknown font style " + style);
            return;
        }

        final Font font = FontStyle.newFontWithStyle(name, style, size);
        final GlyphRange range = new GlyphRange((char) count);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias, false);
        try {
            // TODO Ask if overwrite
            Export.export(new File(outPath), new File(outPath + ".xml"), bitmapFont, false);
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    private static boolean verifyPositiveNonZero(int value, String name) {
        if (value < 1) {
            System.err.println(name + " must be at least 1");
            return false;
        }

        return true;
    }
}
