package org.bfg.form.implement;

import org.bfg.form.base.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class PreviewForm extends Form {

    private PreviewComponent previewComponent;
    private BufferedImage preview;

    @Override
    protected void initForm() {
        this.previewComponent = new PreviewComponent();
        this.add(this.previewComponent);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setPreview(BufferedImage preview) {
        this.preview = preview;
        this.previewComponent.setBounds(0, 0, preview.getWidth(),
            preview.getHeight());
        this.setContentSize(preview.getWidth(), preview.getHeight());
        this.repaint();
    }

    @Override
    protected void onUpdate() {

    }

    private class PreviewComponent extends JPanel {

        @Override
        public void paint(Graphics graphics) {
            super.paint(graphics);

            if (preview != null)
                graphics.drawImage(preview, 0, 0, null);
            else {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
}
