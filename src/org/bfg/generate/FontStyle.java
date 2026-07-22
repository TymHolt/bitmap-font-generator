package org.bfg.generate;

import java.awt.Font;
import java.util.Objects;

public final class FontStyle {

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
