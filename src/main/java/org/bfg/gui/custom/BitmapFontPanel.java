package org.bfg.gui.custom;

import org.bfg.generate.BitmapFont;
import org.bfg.generate.GlyphInfo;
import org.bfg.generate.GlyphRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * A panel to interactively display a generated {@link BitmapFont}.
 */
public final class BitmapFontPanel extends JPanel implements MouseMotionListener, MouseListener {

    private final IGlyphSelectionListener glyphSelectionListener;
    private BitmapFont font;
    private BufferedImage renderImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private Graphics2D renderGraphics = this.renderImage.createGraphics();
    private Rectangle renderArea = new Rectangle(0, 0, 0, 0);
    private GlyphInfo currentSelection = null;
    private boolean showGridFlag = false;

    /**
     * Creates this panel without a {@link IGlyphSelectionListener}.
     */
    public BitmapFontPanel() {
        this(null);
    }

    /**
     * Creates this panel with the given listener, which is called when a glyph is hovered by the mouse.
     *
     * @param glyphSelectionListener The listener to call when the mouse hovers a glyph, may be null.
     */
    public BitmapFontPanel(IGlyphSelectionListener glyphSelectionListener) {
        super();

        if (glyphSelectionListener == null)
            glyphSelectionListener = selection -> {};

        this.glyphSelectionListener = glyphSelectionListener;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    private GlyphInfo getGlyphSelection(int mouseX, int mouseY) {
        if (!this.renderArea.contains(mouseX, mouseY) || this.font == null)
            return null;

        final float xLocal = (float) (mouseX - this.renderArea.x) + 0.5f;
        final float yLocal = (float) (mouseY - this.renderArea.y) + 0.5f;
        final float normalizedX = xLocal / (float) this.renderArea.width;
        final float normalizedY = yLocal / (float) this.renderArea.height;
        final int sourceX = (int) (normalizedX * (float) this.renderImage.getWidth());
        final int sourceY = (int) (normalizedY * (float) this.renderImage.getHeight());
        final int realY = this.font.isYAxisInverted() ? this.font.getAtlasImage().getHeight() - sourceY - 1 : sourceY;

        final GlyphRange range = this.font.getRange();
        for (char c = range.lowEnd; c <= range.highEnd; c++) {
            final GlyphInfo glyphInfo = this.font.getGlyphInfo(c);
            if (glyphBoundsContains(glyphInfo, sourceX, realY + (this.font.isYAxisInverted() ? glyphInfo.height : 0)))
                return glyphInfo;
        }

        return null;
    }

    private static boolean glyphBoundsContains(GlyphInfo glyphInfo, int x, int y) {
        return x >= glyphInfo.x &&
                y >= glyphInfo.y &&
                x < glyphInfo.x + glyphInfo.width &&
                y < glyphInfo.y + glyphInfo.height;
    }

    /**
     * Sets if a grid should be rendered to show the glyph areas.
     *
     * @param flag {@code true} if the grid should be visible.
     */
    public void setShowGrid(boolean flag) {
        this.showGridFlag = flag;
    }

    /**
     * Sets the generated {@link BitmapFont} to display.
     *
     * @param font The font to display, must not be {@code null}.
     */
    public void setBitmapFont(BitmapFont font) {
        Objects.requireNonNull(font);
        this.font = font;

        createRenderImage();
        invalidate();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent event) {}

    @Override
    public void mouseMoved(MouseEvent event) {
        setSelection(getGlyphSelection(event.getX(), event.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        setSelection(null);
    }

    private void setSelection(GlyphInfo selection) {
        this.currentSelection = selection;
        this.glyphSelectionListener.onSelectGlyph(this.currentSelection);
        invalidate();
        repaint();
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
        if (this.currentSelection != null) {
            final int y = this.currentSelection.y;
            final int realY = this.font.isYAxisInverted() ?
                this.font.getAtlasImage().getHeight() - y : y;
            invertRenderImageArea(this.currentSelection.x, realY, this.currentSelection.width,
                this.currentSelection.height);
        }

        // Render to screen
        final int size = Math.min(getWidth(), getHeight());
        final int xOffset = (getWidth() - size) / 2;
        final int yOffset = (getHeight() - size) / 2;
        graphics.drawImage(this.renderImage, xOffset, yOffset, size, size, null);

        if (this.showGridFlag && this.font != null) {
            final Dimension maxGlyphSize = this.font.getMaxGlyphSize();
            graphics.setColor(Color.RED);

            for (int x = maxGlyphSize.width; x < renderWidth; x += maxGlyphSize.width) {
                final float normalizedX = (float) x / (float) renderWidth;
                final int frameX = Math.round(normalizedX * (float) size) + xOffset;
                graphics.drawLine(frameX, yOffset, frameX, yOffset + size - 1);
            }

            for (int y = maxGlyphSize.height; y < renderHeight; y += maxGlyphSize.height) {
                final float normalizedY = (float) y / (float) renderHeight;
                final int frameY = Math.round(normalizedY * (float) size) + yOffset;
                graphics.drawLine(xOffset, frameY, xOffset + size - 1, frameY);
            }
        }

        // Remember where we rendered for handling the mouse input
        this.renderArea = new Rectangle(xOffset, yOffset, size, size);
    }

    private void invertRenderImageArea(int x, int y, int width, int height) {
        final int minX = 0;
        final int minY = 0;
        final int maxX = this.renderImage.getWidth() - 1;
        final int maxY = this.renderImage.getHeight() - 1;

        final int xFrom = Math.clamp(x, minX, maxX);
        final int yFrom = Math.clamp(y, minY, maxY);
        final int xTo = Math.clamp(x + width - 1, minX, maxX);
        final int yTo = Math.clamp(y + height - 1, minY, maxY);

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
        final BufferedImage atlasImage = this.font.getAtlasImage();
        this.renderImage = new BufferedImage(atlasImage.getWidth(), atlasImage.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        this.renderGraphics = this.renderImage.createGraphics();
    }

    /**
     * A listener to handle glyph selection.
     */
    public interface IGlyphSelectionListener {
        void onSelectGlyph(GlyphInfo selection);
    }
}