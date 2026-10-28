package org.bfg.cli;

import java.util.Objects;

/**
 * The arguments possible via CLI.
 */
public enum ArgumentType {

    FONT(new String[] {"-font", "-f"}, "<name>", "The font name"),
    SIZE(new String[] {"-size", "-si"}, "<size>", "The font size"),
    OUT(new String[] {"-out",  "-o"}, "<file-path>", "The output file"),
    STYLE(new String[] {"-style", "-st"}, "<plain/italic/bold>", "The font style"),
    COUNT(new String[] {"-count", "-c"}, "<count>", "Amount of chars to render"),
    ANTIALIAS(new String[] {"-antialias", "-aa"}, "<true/false>", "Enable anti-aliasing");

    /** The forms this argument can be called. */
    private final String[] argumentForms;

    /** The format of the values. */
    private final String valueformat;

    /** A description for the help documentation. */
    private final String description;

    ArgumentType(String[] argumentForms, String valueFormat, String description) {
        this.argumentForms = argumentForms;
        this.valueformat = valueFormat;
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
}
