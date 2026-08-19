package org.bfg.gui.tabs.file.character;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

final class SubImageView extends JPanel {

    private BufferedImage subImage;

    public void setSubImage(int x, int y, int width, int height, BufferedImage image) {
        this.subImage = image.getSubimage(x, y, width, height);
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
