package org.bfg.generate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class BitmapFont {

    private final BufferedImage atlasImage;
    private final GlyphInfo[] infos;
    private final GlyphRange range;
    private final int leading, ascent, descent;
    private final Dimension maxGlyphSize;

    BitmapFont(BufferedImage atlasImage, GlyphInfo[] infos, GlyphRange range, int leading, int ascent, int descent,
       Dimension maxGlyphSize) {
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
    }

    public GlyphInfo getGlyphInfo(char c) {
        if (!this.range.contains(c))
            return null;

        return this.infos[c - this.range.lowEnd];
    }

    public BufferedImage extrudeGlyph(char c) {
        if (!this.range.contains(c))
            return null;

        final GlyphInfo info = getGlyphInfo(c);
        return this.atlasImage.getSubimage(info.x, info.y, Math.max(1, info.width),
            Math.max(1, info.height));
    }

    public int getLeading() {
        return this.leading;
    }

    public int getAscent() {
        return this.ascent;
    }

    public int getDescent() {
        return this.descent;
    }

    public GlyphRange getRange() {
        return this.range;
    }

    public BufferedImage getAtlasImage() {
        return this.atlasImage;
    }

    public Dimension getMaxGlyphSize() {
        return new Dimension(this.maxGlyphSize);
    }
}
