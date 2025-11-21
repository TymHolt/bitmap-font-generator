package org.bfg.generate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public final class BitmapFontGenerator {

    private final char charCount;
    private int leading;
    private int ascent;
    private BufferedImage bitmapFont;
    private Graphics2D graphics;
    private FontMetrics metrics;
    private int charWidth, charHeight, charsPerRow;

    public BitmapFontGenerator(Font font, char charCount) {
        this.charCount = charCount;

        int width = 1, height = 1;
        while (true) {
            width = width * 2;
            height = height * 2;

            this.bitmapFont = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            this.graphics = this.bitmapFont.createGraphics();
            this.graphics.setFont(font);
            this.metrics = this.graphics.getFontMetrics(font);
            this.leading = this.metrics.getLeading();
            this.ascent = this.metrics.getMaxAscent();

            this.charWidth = 1;
            this.charHeight = this.metrics.getMaxAscent() + this.metrics.getMaxDescent();

            for (char c = 0; c < charCount; c++) {
                int nCharWidth = this.metrics.charWidth(c);

                if (nCharWidth > this.charWidth)
                    this.charWidth = nCharWidth;
            }

            this.charsPerRow = this.bitmapFont.getWidth() / charWidth;
            final int rows = (int) Math.ceil((float) charCount / (float) this.charsPerRow);

            if (rows * charHeight <= this.bitmapFont.getHeight() && charsPerRow != 0)
                break;
            else
                this.graphics.dispose();
        }

        this.graphics.setColor(Color.BLACK);
        this.graphics.fillRect(0, 0, width, height);
    }

    public CharInfo generate(char c) {
        if (c >= this.charCount)
            return null;

        final CharInfo charInfo = getCharPosition(c);
        this.graphics.setColor(Color.WHITE);
        this.graphics.drawString(String.valueOf(c), charInfo.x, charInfo.y + charInfo.ascent);

        return charInfo;
    }

    public HashMap<Character, CharInfo> generateAll() {
        final HashMap<Character, CharInfo> charInfoMap = new HashMap<>();
        for (char c = 0; c < this.charCount; c++)
            charInfoMap.put(c, generate(c));

        return charInfoMap;
    }

    public int getLeading() {
        return this.leading;
    }

    public CharInfo getCharPosition(char c) {
        final int column = c % this.charsPerRow;
        final int x = column * this.charWidth;
        final int row = c / this.charsPerRow;
        final int y = row * this.charHeight;
        final int width = this.metrics.charWidth(c);

        return new CharInfo(x, y, width, charHeight, this.ascent);
    }

    public void dispose() {
        this.graphics.dispose();
    }

    public BufferedImage getImage() {
        return this.bitmapFont;
    }
}
