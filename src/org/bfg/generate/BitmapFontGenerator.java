package org.bfg.generate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class BitmapFontGenerator {

    public static BitmapFont generate(Font font, GlyphRange range, boolean antiAliased) {
        Objects.requireNonNull(font, "Font is null");
        Objects.requireNonNull(range, "Range is null");

        // Init small atlas here, so the loop can always free resources at the beginning
        int atlasWidth = 1;
        int atlasHeight = 1;
        BufferedImage atlasImage = createAtlasImage(atlasWidth, atlasHeight);
        Graphics2D atlasGraphics = atlasImage.createGraphics();
        FontMetrics fontMetrics = atlasGraphics.getFontMetrics();
        Dimension maxGlyphSize = new Dimension(1, 1);
        int glyphsPerRow = 0;

        // Find atlas size to fit all glyphs (power of 2 for textures)
        while (true) {
            // Free and init resources
            atlasGraphics.dispose();
            atlasWidth *= 2;
            atlasHeight *= 2;
            atlasImage = createAtlasImage(atlasWidth, atlasHeight);
            atlasGraphics = atlasImage.createGraphics();
            atlasGraphics.setFont(font);
            fontMetrics = atlasGraphics.getFontMetrics();

            // Determine max glyph size
            maxGlyphSize.height = fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent();
            for (char c = range.lowEnd; c <= range.highEnd; c++)
                maxGlyphSize.width = Math.max(maxGlyphSize.width, fontMetrics.charWidth(c));

            // Check if atlas fits all glyphs
            glyphsPerRow = atlasWidth / maxGlyphSize.width;
            final float requiredRowsFloat = (float) range.getCount() / (float) glyphsPerRow;
            final int requiredRows = (int) Math.ceil(requiredRowsFloat);
            final int requiredHeight = requiredRows * maxGlyphSize.height;

            // Atlas size is enough -> break loop
            if (requiredHeight < atlasHeight && glyphsPerRow != 0)
                break;
        }

        clear(atlasGraphics, atlasWidth, atlasHeight);
        atlasGraphics.setColor(Color.WHITE);
        setAntiAliased(atlasGraphics, antiAliased);
        final GlyphInfo[] glyphInfos = new GlyphInfo[range.getCount()];

        // Render glyphs to atlas
        for (char c = range.lowEnd; c <= range.highEnd; c++) {
            final int glyphIndex = c - range.lowEnd;
            final int column = glyphIndex % glyphsPerRow;
            final int row = glyphIndex / glyphsPerRow;
            final int atlasX = column * maxGlyphSize.width;
            final int atlasY = row * maxGlyphSize.height;
            final int baseline = fontMetrics.getMaxAscent() + atlasY;

            atlasGraphics.drawString(String.valueOf(c), atlasX, baseline);

            final int glyphWidth = fontMetrics.charWidth(c);
            final int glyphHeight = maxGlyphSize.height;
            final GlyphInfo glyphInfo = new GlyphInfo(atlasX, atlasY, glyphWidth, glyphHeight);
            glyphInfos[glyphIndex] = glyphInfo;
        }

        atlasGraphics.dispose();
        return new BitmapFont(atlasImage, glyphInfos, range, fontMetrics.getLeading(),
            maxGlyphSize);
    }

    private static BufferedImage createAtlasImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    private static void setAntiAliased(Graphics2D graphics, boolean antiAliased) {
        if (antiAliased)
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }

    private static void clear(Graphics2D graphics, int width, int height) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);
    }
}
