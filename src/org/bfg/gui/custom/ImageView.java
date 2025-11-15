package org.bfg.gui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class ImageView extends JPanel {

    private BufferedImage image;

    public ImageView() {
        this.image = null;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        final int size = Math.min(getWidth(), getHeight());
        final int x = (getWidth() - size) / 2;
        final int y = (getHeight() - size) / 2;

        if (this.image != null) {
            graphics.drawImage(this.image, x, y, size, size, null);
        } else {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(x, y, size, size);
        }
    }
}
