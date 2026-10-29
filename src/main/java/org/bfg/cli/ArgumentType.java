package org.bfg.cli;

import java.util.Objects;

/**
 * The arguments possible via CLI.
 */
enum ArgumentType {

    FONT(new String[] {"-font", "-f"}, "<name>", "The font name"),
    SIZE(new String[] {"-size", "-s"}, "<size>", "The font size"),
    OUT(new String[] {"-out",  "-o"}, "<file-path>", "The output file"),
    OUT_XML(new String[] {"-out-xml",  "-x"}, "<file-path>", "Explicit path for the metadata export"),
    STYLE(new String[] {"-style", "-t"}, "<plain/italic/bold>", "The font style"),
    RANGE_BEGIN(new String[] {"-begin", "-b"}, "<code>", "First char to render"),
    RANGE_END(new String[] {"-end", "-e"}, "<code>", "Last char to render"),
    ANTIALIAS(new String[] {"-antialias", "-a"}, "<true/false>", "Enable anti-aliasing"),
    INVERT_Y(new String[] {"-invert-y", "-y"}, "<true/false>", "Invert Y axis of coordinates"),
    EXPORT_UV(new String[] {"-uv"}, "<true/false>", "Export UV coordinates"),
    OVERWRITE_FORCE(new String[] {"-force"}, "<true/false>", "If files should be overwritten without asking"),
    HELP(new String[] {"-help", "-usage"}, "", "Show usage information");

    /** The forms this argument can be called. */
    private final String[] argumentForms;

    /** The format of the values. */
    private final String valueFormat;

    /** A description for the help documentation. */
    private final String description;

    ArgumentType(String[] argumentForms, String valueFormat, String description) {
        this.argumentForms = argumentForms;
        this.valueFormat = valueFormat;
        this.description = description;
    }

    /**
     * Match the given {@link String} to all argument forms and return the corresponding {@link ArgumentType}.
     *
     * @param argument The argument string to match. Must not be {@code null}.
     * @return The {@link ArgumentType} or {@code null} if none matches.
     */
    public static ArgumentType matchArgument(String argument) {
        Objects.requireNonNull(argument);

        for (ArgumentType type : ArgumentType.values())
            for (String form : type.argumentForms)
                if (form.equals(argument))
                    return type;

        return null;
    }

    /**
     * Builds a help or usage message containing all CLI options.
     *
     * @return The CLI options that are possible.
     */
    public static String createHelpMenuMessage() {
        int formMax = 0;
        int valueMax = 0;

        for (ArgumentType type : ArgumentType.values()) {
            for (String form : type.argumentForms)
                formMax = Math.max(formMax, form.length());

            valueMax = Math.max(valueMax, type.valueFormat.length());
        }

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Usage: java -jar <jar-name> <...>\n\n");

        for (ArgumentType type : ArgumentType.values()) {
            for (String form : type.argumentForms) {
                stringBuilder.append(form);

                for (int count = 0; count < formMax - form.length() + 1; count++)
                    stringBuilder.append(' ');

                stringBuilder.append(type.valueFormat);

                for (int count = 0; count < valueMax - type.valueFormat.length() + 1; count++)
                    stringBuilder.append(' ');

                stringBuilder.append(type.description);

                stringBuilder.append('\n');
            }

            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
