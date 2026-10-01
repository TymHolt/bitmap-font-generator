package org.bfg.gui.tabs.file.character;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Panel that paints selected region from a given image, scaled to the component size respecting the aspect ratio.
 */
final class SubImageView extends JPanel {

    private BufferedImage subImage;

    /**
     * Crops the selected region from the {@code image} and triggers a component repaint. Invalid bounds may result in
     * exceptions being thrown by the underlying {@code awt} classes.
     *
     * @param x      X coordinate of the region bounds in pixels.
     * @param y      Y coordinate of the region bounds in pixels.
     * @param width  Width of the region bounds in pixels.
     * @param height Height of the region bounds in pixels.
     * @param image  The source image containing the region, must not be {@code null}.
     */
    public void setSubImage(int x, int y, int width, int height, BufferedImage image) {
        this.subImage = image.getSubimage(x, y, width, height);
        repaint();
    }

    /**
     * Clears the buffered region and triggers a component repaint.
     */
    public void resetSubImage() {
        this.subImage = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.subImage == null)
            return;

        final float subImageRatio = (float) this.subImage.getWidth() / (float) this.subImage.getHeight();
        final int renderWidth = (int) ((float) getHeight() * subImageRatio);
        graphics.drawImage(subImage, 0, 0, renderWidth, getHeight(), this);
    }
}
