package org.bfg.gui;

import org.bfg.Context;
import org.bfg.generate.BitmapFont;
import org.bfg.generate.GlyphInfo;
import org.bfg.generate.GlyphRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class BitmapFontView extends JPanel {

    private final Context context;
    private BitmapFont font;
    private BufferedImage renderImage;
    private Graphics2D renderGraphics;
    private Rectangle renderArea;
    private Rectangle highlightArea;

    public BitmapFontView(Context context) {
        this.context = context;
        this.renderImage = null;
        this.renderGraphics = null;
        this.highlightArea = null;
        this.renderArea = new Rectangle(0, 0, 0, 0);

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                highlightArea = null;

                final int x = mouseEvent.getX();
                final int y = mouseEvent.getY();

                if (!renderArea.contains(x, y) || font == null)
                    return;

                final float xLocal = (float) (x - renderArea.x) + 0.5f;
                final float yLocal = (float) (y - renderArea.y) + 0.5f;
                final float normalizedX = xLocal / (float) renderArea.width;
                final float normalizedY = yLocal / (float) renderArea.height;
                final int sourceX = (int) (normalizedX * (float) renderImage.getWidth());
                final int sourceY = (int) (normalizedY * (float) renderImage.getHeight());

                final GlyphRange range = font.getRange();
                for (char c = range.lowEnd; c <= range.highEnd; c++) {
                    final GlyphInfo glyphInfo = font.getGlyphInfo(c);
                    final Rectangle glyphBounds = new Rectangle(
                        glyphInfo.x,
                        glyphInfo.y,
                        glyphInfo.width,
                        glyphInfo.height);

                    if (glyphBounds.contains(sourceX, sourceY)) {
                        highlightArea = glyphBounds;
                        break;
                    }
                }

                invalidate();
                repaint();
            }
        });
    }

    public void setFont(BitmapFont font) {
        Objects.requireNonNull(font, "Font is null");

        this.font = font;
        createRenderImage();

        invalidate();
        repaint();
    }

    public BitmapFont getBitmapFont() {
        return this.font;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        // Render to buffer
        final int renderWidth = this.renderImage.getWidth();
        final int renderHeight = this.renderImage.getHeight();

        if (this.font == null) {
            this.renderGraphics.setColor(Color.BLACK);
            this.renderGraphics.fillRect(0, 0, renderWidth, renderHeight);
        } else
            this.renderGraphics.drawImage(this.font.getAtlasImage(), 0, 0, null);

        // Render mouse highlight
        if (this.highlightArea != null) {
            invertRenderImageArea(this.highlightArea.x, this.highlightArea.y,
                this.highlightArea.width, this.highlightArea.height);
        }

        // Render grid
        if (this.context.shouldShowGrid() && this.font != null) {
            final Dimension maxGlyphSize = this.font.getMaxGlyphSize();
            this.renderGraphics.setColor(Color.RED);

            for (int x = maxGlyphSize.width; x < renderWidth; x += maxGlyphSize.width)
                this.renderGraphics.drawLine(x, 0, x, renderHeight - 1);

            for (int y = maxGlyphSize.height; y < renderHeight; y += maxGlyphSize.height)
                this.renderGraphics.drawLine(0, y, renderWidth - 1, y);
        }

        // Render to screen
        final int size = Math.min(getWidth(), getHeight());
        final int x = (getWidth() - size) / 2;
        final int y = (getHeight() - size) / 2;
        graphics.drawImage(this.renderImage, x, y, size, size, null);

        // Remember where we rendered for handling the mouse input
        this.renderArea = new Rectangle(x, y, size, size);
    }

    private void invertRenderImageArea(int x, int y, int width, int height) {
        final int minX = 0;
        final int minY = 0;
        final int maxX = this.renderImage.getWidth() - 1;
        final int maxY = this.renderImage.getHeight() - 1;

        final int xFrom = Math.max(minX, Math.min(maxX, x));
        final int yFrom = Math.max(minY, Math.min(maxY, y));
        final int xTo = Math.max(minX, Math.min(maxX, x + width - 1));
        final int yTo = Math.max(minY, Math.min(maxY, y + height - 1));

        for (int currentX = xFrom; currentX <= xTo; currentX++)
            for (int currentY = yFrom; currentY <= yTo; currentY++) {
                Color color = new Color(this.renderImage.getRGB(currentX, currentY));

                color = new Color(
                    255 - color.getRed(),
                    255 - color.getGreen(),
                    255 - color.getBlue());

                this.renderImage.setRGB(currentX, currentY, color.getRGB());
            }
    }

    private void createRenderImage() {
        if (this.renderGraphics != null)
            this.renderGraphics.dispose();

        final BufferedImage atlasImage = this.font.getAtlasImage();
        this.renderImage = new BufferedImage(atlasImage.getWidth(), atlasImage.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        this.renderGraphics = this.renderImage.createGraphics();
    }
}
