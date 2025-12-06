package org.bfg;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.BitmapFontGenerator;
import org.bfg.generate.Export;
import org.bfg.generate.FontStyle;
import org.bfg.generate.GlyphRange;

import javax.swing.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Main {

    private static boolean mainCalled = false;

    public static void main(String[] args) {
        if (mainCalled)
            throw new IllegalStateException("Main method called again");
        mainCalled = true;

        if (args.length > 0)
            cliMode(args);
        else
            guiMode();
    }

    private static void cliMode(String[] args) {
        if (args.length < 2 || args.length % 2 != 0) {
            printUsage();
            return;
        }

        final HashMap<ArgumentType, String> arguments = new HashMap<>();
        for (int index = 1; index < args.length; index += 2) {
            final String argumentTypeRaw = args[index - 1];
            final ArgumentType argumentType = ArgumentType.getType(argumentTypeRaw);

            if (argumentType == null) {
                System.err.println("Unknown argument " + argumentTypeRaw);
                return;
            }

            if (arguments.containsKey(argumentType)) {
                System.err.println("Argument " + argumentType.longForm + " defined again");
                return;
            }

            arguments.put(argumentType, args[index]);
        }

        for (ArgumentType requiredType : ArgumentType.values()) {
            if (!requiredType.required)
                continue;

            if (!arguments.containsKey(requiredType)) {
                System.err.println("Argument " + requiredType.longForm + " is required");
                return;
            }
        }

        String name = "";
        int style = Font.PLAIN;
        int size = 20;
        int count = 256;
        boolean antiAlias = false;
        String outPath = "";

        for (ArgumentType argumentType : arguments.keySet()) {
            final String stringValue = arguments.get(argumentType);
            switch (argumentType) {
                case FONT:
                    name = stringValue;
                    break;
                case SIZE:
                    try {
                        size = Integer.parseInt(stringValue);
                        if (size < 1)
                            throw new NumberFormatException("Is negative");
                    } catch (Exception exception) {
                        System.err.println("Wrong number format: " + stringValue);
                        return;
                    }
                    break;
                case OUT:
                    outPath = stringValue;
                    break;
                case STYLE:
                    try {
                        style = FontStyle.getId(stringValue);
                    } catch (Exception exception) {
                        System.err.println("Unknown style: " + stringValue);
                        return;
                    }
                    break;
                case COUNT:
                    try {
                        count = Integer.parseInt(stringValue);
                        if (count < 1)
                            throw new NumberFormatException("Is negative");
                    } catch (Exception exception) {
                        System.err.println("Wrong number format: " + stringValue);
                        return;
                    }
                    break;
                case ANTIALIAS:
                    try {
                        antiAlias = Boolean.parseBoolean(stringValue);
                    } catch (Exception exception) {
                        System.err.println("Wrong boolean format: " + stringValue);
                        return;
                    }
                    break;
            }
        }

        final Font font = new Font(name, style, count);
        final GlyphRange range = new GlyphRange((char) count);
        final BitmapFont bitmapFont = BitmapFontGenerator.generate(font, range, antiAlias);
        try {
            Export.export(new File(outPath), bitmapFont);
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    private static void printUsage() {
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
    }

    private static void guiMode() {
        try {
            new Context();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private enum ArgumentType {
        FONT("-font", "-f", "<name>", "The font name", true),
        SIZE("-size", "-si", "<size>", "The font size", true),
        OUT("-out",  "-o", "<file-path>", "The output file", true),
        STYLE("-style", "-st", "<plain/italic/bold>", "The font style", false),
        COUNT("-count", "-c", "<count>", "Amount of chars to render", false),
        ANTIALIAS("-antialias", "-aa", "<true/false>", "Enable anti-aliasing", false);

        final String longForm;
        final String shortForm;
        final String parameterName;
        final String description;
        final boolean required;

        ArgumentType(String longForm, String shortForm, String parameterName, String description,
            boolean required) {
            this.longForm = longForm;
            this.shortForm = shortForm;
            this.parameterName = parameterName;
            this.description = description;
            this.required = required;
        }

        boolean matchForm(String form) {
            final String formLower = form.toLowerCase();
            return this.longForm.equals(formLower) || this.shortForm.equals(formLower);
        }

        static ArgumentType getType(String form) {
            for (ArgumentType argumentType : values())
                if (argumentType.matchForm(form))
                    return argumentType;

            return null;
        }
    }
}
