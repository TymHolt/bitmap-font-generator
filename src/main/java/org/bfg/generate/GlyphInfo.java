package org.bfg.generate;

/**
 * A collection of metadata for a single glyph in a {@link BitmapFont}. Supposed to be generated and provided by the
 * font object.
 */
public final class GlyphInfo {

    /** Character this glyph metadata is for. */
    public final char charValue;

    /** X coordinate (left edge) of this glyph on the rendered atlas in pixels. */
    public final int x;

    /** Y coordinate of this glyph on the rendered atlas in pixels. Top or bottom depending on if the y-axis is inverted
     *  for the font.
     */
    public final int y;

    /** Glyph width on the atlas in pixels. */
    public final int width;

    /** Glyph height on the atlas in pixels. */
    public final int height;

    /** Normalized X-coordinate on the atlas. */
    public final float u;

    /** Normalized Y-coordinate on the atlas. */
    public final float v;

    /** Normalized width on the atlas. */
    public final float uWidth;

    /** Normalized height on the atlas. */
    public final float vHeight;

    /**
     * Creates and stores glyph metadata for one character.
     *
     * @param charValue The character the data is for.
     * @param x         The x coordinate on the generated atlas.
     * @param y         The y coordinate on the generated atlas.
     * @param width     The width on the generated atlas.
     * @param height    The height on the generated atlas.
     * @param u         Normalized X coordinate.
     * @param v         Normalized Y coordinate.
     * @param uWidth    Normalized width.
     * @param vHeight   Normalized height.
     */
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
