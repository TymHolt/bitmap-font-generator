package org.bfg.cli;

import java.util.Objects;

/**
 * The arguments possible via CLI.
 */
enum ArgumentType {

    FONT(new String[] {"-font", "-f"}, "<name>", "The font name"),
    SIZE(new String[] {"-size", "-s"}, "<size>", "The font size"),
    OUT(new String[] {"-out",  "-o"}, "<file-path>", "The output file"),
    STYLE(new String[] {"-style", "-t"}, "<plain/italic/bold>", "The font style"),
    RANGE_BEGIN(new String[] {"-begin", "-b"}, "<code>", "First char to render"),
    RANGE_END(new String[] {"-end", "-e"}, "<code>", "Last char to render"),
    ANTIALIAS(new String[] {"-antialias", "-a"}, "<true/false>", "Enable anti-aliasing"),
    INVERT_Y(new String[] {"-invert-y", "-y"}, "<true/false>", "Invert Y axis of coordinates"),
    EXPORT_UV(new String[] {"-uv"}, "<true/false>", "Export UV coordinates");

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
