package org.bfg.generate;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class BitmapFontGenerator {

    private final int charCount;
    private int charIndex;
    private BufferedImage bitmapFont;
    private Graphics2D graphics;
    private FontMetrics metrics;
    private int charWidth, charHeight, charsPerRow;

    public BitmapFontGenerator(Font font, int charCount) {
        this.charCount = charCount;
        this.charIndex = 0;

        int width = 1, height = 1;
        while (true) {
            width = width * 2;
            height = height * 2;

            this.bitmapFont = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            this.graphics = this.bitmapFont.createGraphics();
            this.graphics.setFont(font);
            this.metrics = this.graphics.getFontMetrics(font);

            this.charWidth = 1;
            this.charHeight = this.metrics.getMaxAscent() + this.metrics.getMaxDescent();

            for (int charNr = 0; charNr < charCount; charNr++) {
                int nCharWidth = this.metrics.charWidth((char) charNr);

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
    }

    public void generateNext() {
        if (this.charIndex >= this.charCount)
            return;

        final int column = this.charIndex % this.charsPerRow;
        final int x = column * this.charWidth;
        final int row = this.charIndex / this.charsPerRow;
        final int y = row * this.charHeight + this.metrics.getMaxAscent();

        this.graphics.setColor(Color.WHITE);
        this.graphics.drawString("" + (char) this.charIndex, x, y);

        this.charIndex++;
    }

    public void dispose() {
        this.graphics.dispose();
    }

    public BufferedImage getImage() {
        return this.bitmapFont;
    }
}
