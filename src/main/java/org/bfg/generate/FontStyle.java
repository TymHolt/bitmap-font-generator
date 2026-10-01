package org.bfg.generate;

import java.awt.Font;
import java.util.Objects;

/**
 * Utilities for styling {@link Font} objects.
 */
public final class FontStyle {

    /**
     * Returns the names of possible font styles.
     *
     * @return A new array of possible style names.
     */
    public static String[] getAllValues() {
        return new String[] {"Plain", "Italic", "Bold"};
    }

    /**
     * Creates an AWT {@link Font} with the given options.
     *
     * @param fontName  The name of the font, must not be {@code null}.
     * @param styleName The case-insensitive name of the font style, must not be {@code null}.
     * @param size      The point size of the font.
     * @return A new {@link Font}.
     * @throws NullPointerException     If a needed argument is {@code null}.
     * @throws IllegalArgumentException If {@code styleName} is unknown.
     */
    public static Font newFontWithStyle(String fontName, String styleName, int size) {
        Objects.requireNonNull(fontName);
        Objects.requireNonNull(styleName);

        return switch (styleName.toLowerCase()) {
            case "plain" -> new Font(fontName, Font.PLAIN, size);
            case "italic" -> new Font(fontName, Font.ITALIC, size);
            case "bold" -> new Font(fontName, Font.BOLD, size);
            default -> throw new IllegalArgumentException("Style unknown: '" + styleName + "'");
        };
    }
}
