package org.bfg.generate;

public final class GlyphInfo {

    public final char charValue;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final float u;
    public final float v;
    public final float uWidth;
    public final float vHeight;

    GlyphInfo(char charValue, int x, int y, int width, int height, float u, float v, float uWidth, float vHeight) {
        this.charValue = charValue;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
        this.uWidth = uWidth;
        this.vHeight = vHeight;
    }
}
