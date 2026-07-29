package org.bfg.generate;

public final class GlyphInfo {

    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final char charValue;

    GlyphInfo(char charValue, int x, int y, int width, int height) {
        this.charValue = charValue;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
