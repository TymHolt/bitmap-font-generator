package org.bfg.form.base;

import javax.swing.*;
import java.awt.*;

public abstract class Form extends JFrame {

    private boolean formFinalized;
    private boolean contentSizeRequested;
    private int requestedContentWidth;
    private int requestedContentHeight;

    public Form() {
        super("Form");
        this.formFinalized = false;
        this.contentSizeRequested = false;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        this.initForm();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.formFinalized = true;

        if (this.contentSizeRequested)
            setContentSize(requestedContentWidth, requestedContentHeight);
    }

    protected abstract void initForm();
    protected abstract void onUpdate();

    public final void update() {
        this.repaint();
    }

    @Override
    public void paint(Graphics graphics) {
        this.onUpdate();
        super.paint(graphics);
    }

    protected void setContentSize(int width, int height) {
        if (!this.formFinalized) {
            this.requestedContentWidth = width;
            this.requestedContentHeight = height;
            this.contentSizeRequested = true;
            return;
        }

        Insets insets = this.getInsets();
        final int totalWidth = insets.left + width + insets.right;
        final int totalHeight = insets.top + height + insets.bottom;
        this.setSize(totalWidth, totalHeight);
    }
}
