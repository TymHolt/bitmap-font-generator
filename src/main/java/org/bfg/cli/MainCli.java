package org.bfg.cli;

import org.bfg.generate.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
        final String name;
        final String style;
        final int size;
        final int rangeBegin;
        final int rangeEnd;
        final String outPath;
        final String outXmlPath;
        final boolean antiAlias;
        final boolean invertYAxis;
        final boolean exportUV;
        final boolean forceOverwrite;

        final CliArguments arguments;
        try {
            arguments = CliArguments.parse(args);
            name = arguments.parseSingleStringValue(ArgumentType.FONT, "");
            style = arguments.parseSingleStringValue(ArgumentType.STYLE, "plain");
            size = arguments.parseSingleIntegerValue(ArgumentType.SIZE, 20);
            rangeBegin = arguments.parseSingleIntegerValue(ArgumentType.RANGE_BEGIN, 0);
            rangeEnd = arguments.parseSingleIntegerValue(ArgumentType.RANGE_END, 255);
            outPath = arguments.parseSingleStringValue(ArgumentType.OUT, null);
            outXmlPath = arguments.parseSingleStringValue(ArgumentType.OUT_XML, null);
            antiAlias = arguments.parseSingleBooleanValue(ArgumentType.ANTIALIAS, false);
            invertYAxis = arguments.parseSingleBooleanValue(ArgumentType.INVERT_Y, false);
            exportUV = arguments.parseSingleBooleanValue(ArgumentType.EXPORT_UV, false);
            forceOverwrite = arguments.parseSingleBooleanValue(ArgumentType.OVERWRITE_FORCE, false);
        } catch (CliArguments.CliParseException exception) {
            System.err.println(exception.getMessage());
            return;
        }

        if (outPath == null) {
            System.err.println("No file path for export given");
            return;
        }

        if (!verifyMinimum(size, 1, "Font size"))
            return;

        if (!verifyMinimum(rangeBegin, 0, "Range begin"))
            return;

        if (!verifyMinimum(rangeEnd, 1, "Range end"))
            return;

        if (rangeBegin > rangeEnd) {
            System.err.println("Range begin must be less than range end");
            return;
        }

        // TODO Hacky to test for valid font style
        try {
            FontStyle.newFontWithStyle(name, style, size);
        } catch (Exception exception) {
            System.err.println("Unknown font style " + style);
            return;
        }

        final Font font = FontStyle.newFontWithStyle(name, style, size);
        final GlyphRange range = new GlyphRange((char) rangeBegin, (char) rangeEnd);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias, invertYAxis);
        try {
            final File imageFile = new File(outPath);
            final File xmlFile = new File(outXmlPath != null ? outXmlPath : outPath + ".xml");

            if (!forceOverwrite) {
                if (imageFile.exists() || xmlFile.exists())
                    if (!askCliProceed("File already exists, overwrite?", false)) {
                        System.out.println("Aborting...");
                        return;
                    }
            }

            Export.export(imageFile, xmlFile, bitmapFont, exportUV);
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    private static boolean askCliProceed(String message, boolean fallback) {
        System.out.print(message + " (Yes/No): ");
        final String answer = new Scanner(System.in).nextLine().toLowerCase();

        if (answer.equals("yes") || answer.equals("y"))
            return true;

        if (answer.equals("no") || answer.equals("n"))
            return false;

        return fallback;
    }

    private static boolean verifyMinimum(int value, int min, String name) {
        if (value < min) {
            System.err.println(name + " must be at least " + min);
            return false;
        }

        return true;
    }
}
