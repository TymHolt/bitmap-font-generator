package org.bfg.generate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class BitmapFont {

    private final BufferedImage atlasImage;
    private final GlyphInfo[] infos;
    private final GlyphRange range;
    private final int leading;
    private final Dimension maxGlyphSize;

    BitmapFont(BufferedImage atlasImage, GlyphInfo[] infos, GlyphRange range, int leading,
        Dimension maxGlyphSize) {
        Objects.requireNonNull(atlasImage, "Atlas image is null");
        Objects.requireNonNull(infos, "Glyph infos is null");
        Objects.requireNonNull(range, "Glyph range is null");
        Objects.requireNonNull(maxGlyphSize, "Max glyph size is null");

        if (leading < 0)
            throw new IllegalArgumentException("Leading is negative");

        if (range.getCount() != infos.length)
            throw new IllegalArgumentException("Range mismatch");

        this.atlasImage = atlasImage;
        this.infos = infos;
        this.range = range;
        this.leading = leading;
        this.maxGlyphSize = maxGlyphSize;
    }

    public GlyphInfo getGlyphInfo(char c) {
        if (!this.range.contains(c))
            return null;

        return this.infos[c + this.range.lowEnd];
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
