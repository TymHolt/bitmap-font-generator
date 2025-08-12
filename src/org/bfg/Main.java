package org.bfg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Main {

    private static boolean mainCalled = false;

    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");

        mainCalled = true;

        try {
            run(args);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.out.println("Usage: [Font name] [Font style] [Font size] [Char count] [Output File]");
        }
    }

    private static void run(String[] args) throws IOException {
        if (args.length != 5)
            throw new IllegalStateException("Wrong amount of arguments");

        final String fontName = args[0];
        final int fontStyleId = getStyleId(args[1]);
        final int fontSize = parseIntGreaterOne(args[2]);
        final int charCount = parseIntGreaterOne(args[3]);
        final String outputFileName = args[4];

        final Font font = new Font(fontName, fontStyleId, fontSize);
        final BitmapFontGenerator bitmapFontGenerator = new BitmapFontGenerator(font);
        final BufferedImage bitmapFont = bitmapFontGenerator.generate(charCount);

        ImageIO.write(bitmapFont, "PNG", new File(outputFileName));
    }

    private static int getStyleId(String name) {
        return switch (name.toLowerCase()) {
            case "plain" -> Font.PLAIN;
            case "italic" -> Font.ITALIC;
            case "bold" -> Font.BOLD;
            default -> throw new IllegalArgumentException("Style unknown: '" + name + "'");
        };
    }

    private static int parseIntGreaterOne(String stringValue) {
        final int result = Integer.parseInt(stringValue);

        if (result < 1)
            throw new IllegalStateException("Number is negative");

        return result;
    }
}
