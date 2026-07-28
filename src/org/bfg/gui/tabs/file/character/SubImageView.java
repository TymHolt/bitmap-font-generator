package org.bfg.gui.tabs.file.character;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

final class SubImageView extends JPanel {

    private BufferedImage subImage;

    public void setSubImage(Rectangle bounds, BufferedImage image) {
        this.subImage = image.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height);
        repaint();
    }

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
