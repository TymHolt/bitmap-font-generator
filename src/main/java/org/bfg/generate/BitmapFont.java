package org.bfg.generate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * An immutable representation of a bitmap font, containing a rendered atlas image and the corresponding glyph metrics.
 * This class is intended to be provided by a {@link BitmapFontGenerator}.
 */
public final class BitmapFont {

    private final BufferedImage atlasImage;
    private final GlyphInfo[] infos;
    private final GlyphRange range;
    private final int leading, ascent, descent;
    private final Dimension maxGlyphSize;
    private final boolean invertedYAxis;

    /**
     * Creates a bitmap font object with the given data. The data is only verified on a basic level.
     *
     * @param atlasImage    The atlas image containing the rendered glyphs. Must not be {@code null}.
     * @param infos         The glyph metrics. Must not be {@code null}.
     * @param range         The range of glyphs contained in the font. Must not be {@code null}.
     * @param leading       The inter-line spacing of the font. Must be {@code >= 0}.
     * @param ascent        The typical glyph size above the text baseline. Must be {@code >= 0}.
     * @param descent       The typical glyph size beneath the text baseline.Must be {@code >= 0}.
     * @param maxGlyphSize  The maximum size of a glyph. Must not be {@code null}.
     * @param invertedYAxis If the glyph coordinates are inverted on the Y-axis.
     * @throws NullPointerException     If any required argument is {@code null}.
     * @throws IllegalArgumentException If any required argument is out of bounds.
     */
    BitmapFont(BufferedImage atlasImage, GlyphInfo[] infos, GlyphRange range, int leading, int ascent, int descent,
       Dimension maxGlyphSize, boolean invertedYAxis) {
        Objects.requireNonNull(atlasImage);
        this.atlasImage = atlasImage;

        Objects.requireNonNull(infos);
        this.infos = infos;

        Objects.requireNonNull(range);
        if (range.getCount() != infos.length)
            throw new IllegalArgumentException("Range mismatch");
        this.range = range;

        if (leading < 0 || ascent < 0 || descent < 0)
            throw new IllegalArgumentException("Value us negative");
        this.leading = leading;
        this.ascent = ascent;
        this.descent = descent;

        Objects.requireNonNull(maxGlyphSize);
        this.maxGlyphSize = maxGlyphSize;

        this.invertedYAxis = invertedYAxis;
    }

    /**
     * Returns the metrics for the given character.
     *
     * @param c The character to get the metrics for.
     * @return The corresponding {@link GlyphInfo} for the given character. Returns {@code null} if {@code c}
     *         is not included in this font.
     */
    public GlyphInfo getGlyphInfo(char c) {
        if (!this.range.contains(c))
            return null;

        return this.infos[c - this.range.lowEnd];
    }

    /**
     * Creates a new sub-image from this fonts atlas containing the glyph for the given character.
     *
     * @param c The character to be included in the sub-image.
     * @return A new {@link BufferedImage} as the sub-image for the character. Returns {@code null} if {@code c}
     *         is not included in this font.
     */
    public BufferedImage extrudeGlyph(char c) {
        if (!this.range.contains(c))
            return null;

        // TODO This does not take inverted-Y into account
        final GlyphInfo info = getGlyphInfo(c);
        return this.atlasImage.getSubimage(info.x, info.y, Math.max(1, info.width),
            Math.max(1, info.height));
    }

    /**
     * Returns the interline spacing between text lines, meaning the distance between on glyphs ascent and another
     * glyphs descent.
     *
     * @return The fonts leading in pixels.
     */
    public int getLeading() {
        return this.leading;
    }

    /**
     * Returns the typical ascent of a glyph, meaning the distance from the text baseline to the glyphs top.
     *
     * @return The typical ascent of a glyph in pixels.
     */
    public int getAscent() {
        return this.ascent;
    }

    /**
     * Returns the typical descent of a glyph, meaning the distance from the text baseline to the glyphs bottom.
     *
     * @return The typical descent of a glyph in pixels.
     */
    public int getDescent() {
        return this.descent;
    }

    /**
     * Returns the range of generated glyphs in this font, having a lower and higher bound.
     *
     * @return The {@link GlyphRange} contained in this font, never {@code null}.
     */
    public GlyphRange getRange() {
        return this.range;
    }

    /**
     * Returns the generated atlas image that contains all rendered glyphs. This function exposes the true image object
     * that is stored in this font and is never {@code null}.
     *
     * @return The generated {@link BufferedImage} stored in this font.
     */
    public BufferedImage getAtlasImage() {
        return this.atlasImage;
    }

    /**
     * Returns the maximum size of a glyph in this font.
     *
     * @return A new {@link Dimension} with the maximum glyph size.
     */
    public Dimension getMaxGlyphSize() {
        return new Dimension(this.maxGlyphSize);
    }

    /**
     * Returns whether the glyph coordinates are inverted on the Y-axis.
     *
     * @return If the glyph coordinates are inverted on the Y-axis.
     */
    public boolean isYAxisInverted() {
        return this.invertedYAxis;
    }
}
