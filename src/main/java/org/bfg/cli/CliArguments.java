package org.bfg.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A representation of command-line-arguments in a parsed form.
 */
final class CliArguments {

    private final HashMap<ArgumentType, List<String>> argumentValues = new HashMap<>();

    /**
     * Add the argument type with no values assigned. If the argument already has values assigned, nothing is done.
     *
     * @param type The argument type. Must not be {@code null}.
     */
    public void addArgument(ArgumentType type) {
        if (!this.argumentValues.containsKey(type))
            this.argumentValues.put(type, new ArrayList<>());
    }

    /**
     * Add a value to a given argument type.
     *
     * @param type  The argument type. Must not be {@code null}.
     * @param value The value added to this type. Must not be {@code null}.
     */
    public void addValue(ArgumentType type, String value) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(value);

        if (!this.argumentValues.containsKey(type)) {
            final List<String> values = new ArrayList<>();
            values.add(value);
            this.argumentValues.put(type, values);
        } else
            this.argumentValues.get(type).add(value);

    }

    /**
     * @param type The type to fetch the values for. Must not be {@code null}.
     * @return     The values assigned to the given type, an empty list if none were assigned.
     */
    public List<String> getValues(ArgumentType type) {
        Objects.requireNonNull(type);

        if (this.argumentValues.containsKey(type))
            return this.argumentValues.get(type);
        else
            return new ArrayList<>();
    }

    /**
     * Parse the values for the given {@link ArgumentType}. If more than one value is assigned to the type or if the
     * argument cannot be parsed to the expected data type an {@link CliParseException} is thrown.
     *
     * @param  type              The type to parse the values for. Must not be {@code null}.
     * @param  fallback          This value will be returned if the argument has no value assigned.
     * @return                   The parsed data value.
     * @throws CliParseException If the parsing fails.
     */
    public String parseSingleStringValue(ArgumentType type, String fallback) throws CliParseException {
        Objects.requireNonNull(type);
        final List<String> values = getValues(type);
        final int valueCount = values.size();

        if (valueCount == 0)
            return fallback;

        if (valueCount > 1)
            throw new CliParseException("Expected one value for argument " + type.name() + ", found " + valueCount);

        return values.getFirst();
    }

    /**
     * Parse the values for the given {@link ArgumentType}. If more than one value is assigned to the type or if the
     * argument cannot be parsed to the expected data type an {@link CliParseException} is thrown.
     *
     * @param  type              The type to parse the values for. Must not be {@code null}.
     * @param  fallback          This value will be returned if the argument has no value assigned.
     * @return                   The parsed data value.
     * @throws CliParseException If the parsing fails.
     */
    public int parseSingleIntegerValue(ArgumentType type, int fallback) throws CliParseException {
        Objects.requireNonNull(type);
        final List<String> values = getValues(type);
        final int valueCount = values.size();

        if (valueCount == 0)
            return fallback;

        if (valueCount > 1)
            throw new CliParseException("Expected one value for argument " + type.name() + ", found " + valueCount);

        final String value = values.getFirst();
        try {
            return Integer.parseInt(value);
        } catch (Exception exception) {
            throw new CliParseException("Value " + value + " cannot be parsed as integer");
        }
    }

    /**
     * Parse the values for the given {@link ArgumentType}. If more than one value is assigned to the type or if the
     * argument cannot be parsed to the expected data type an {@link CliParseException} is thrown.
     *
     * @param  type              The type to parse the values for. Must not be {@code null}.
     * @param  fallback          This value will be returned if the argument has no value assigned.
     * @return                   The parsed data value.
     * @throws CliParseException If the parsing fails.
     */
    public boolean parseSingleBooleanValue(ArgumentType type, boolean fallback) throws CliParseException {
        Objects.requireNonNull(type);
        final List<String> values = getValues(type);
        final int valueCount = values.size();

        if (valueCount == 0)
            return fallback;

        if (valueCount > 1)
            throw new CliParseException("Expected one value for argument " + type.name() + ", found " + valueCount);

        final String value = values.getFirst();
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception exception) {
            throw new CliParseException("Value " + value + " cannot be parsed as boolean");
        }
    }

    /**
     * Check if the given {@link ArgumentType} is present in the parsed arguments.
     *
     * @param  type The type to check for. Must not be {@code null}.
     * @return {@code true} if the argument type was parsed.
     */
    public boolean isArgumentTypePresent(ArgumentType type) {
        Objects.requireNonNull(type);
        return this.argumentValues.containsKey(type);
    }

    /**
     * Parse raw CLI arguments into a {@link CliArguments} instance.
     *
     * @param  args              The raw CLI arguments.
     * @return                   The parsed instance.
     * @throws CliParseException If the parsing fails.
     */
    public static CliArguments parse(String[] args) throws CliParseException {
        Objects.requireNonNull(args);

        final CliArguments arguments = new CliArguments();
        ArgumentType currentType = null;
        for (String value : args) {
            if (currentType == null || value.startsWith("-")) {
                currentType = expectArgumentType(value);
                arguments.addArgument(currentType);
                continue;
            }

            arguments.addValue(currentType, value);
        }

        return arguments;
    }

    private static ArgumentType expectArgumentType(String value) throws CliParseException {
        final ArgumentType type = ArgumentType.matchArgument(value);

        if (type == null)
            throw new CliParseException("Unknown argument " + value);

        return type;
    }

    /**
     * This exception is thrown when CLI-Arguments cannot be parsed.
     */
    public static class CliParseException extends Exception {

        CliParseException(String message) {
            super(message);
        }
    }
}
