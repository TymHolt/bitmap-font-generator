package org.bfg.form.implement;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class PreviewForm extends JFrame {

    private BufferedImage preview;

    public PreviewForm() {
        super("Preview");
        this.add(new PreviewComponent());
        this.setSize(300, 300 + 60);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }

    public void setPreview(BufferedImage preview) {
        this.preview = preview;
        this.setSize(preview.getWidth(), preview.getHeight() + 60);
        this.repaint();
    }

    private class PreviewComponent extends JPanel {

        @Override
        public void paint(Graphics graphics) {
            if (preview != null)
                graphics.drawImage(preview, 0, 0, null);
            else {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
}
