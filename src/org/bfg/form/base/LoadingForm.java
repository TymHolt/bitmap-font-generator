package org.bfg.form.base;

import javax.swing.*;

public abstract class LoadingForm extends Form {

    private JLabel label;
    private JProgressBar progressBar;

    @Override
    protected void initForm() {
        this.setTitle("Loading...");

        final int padding = 5;

        this.label = new JLabel("Loading...");
        this.label.setBounds(padding, padding, 300, 20);
        this.add(this.label);

        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setBounds(
            padding,
            padding + (int) this.label.getBounds().getHeight() + padding,
            400,
            30);
        this.progressBar.setStringPainted(true);
        this.progressBar.setValue(0);
        this.add(progressBar);

        this.setContentSize(
            padding + (int) this.progressBar.getBounds().getWidth() + padding,
            (int) (this.progressBar.getBounds().getY() + this.progressBar.getBounds().getHeight())
                    + padding);
    }

    @Override
    protected void onUpdate() {

    }

    protected void setProgressMax(int max) {
        this.progressBar.setMaximum(max);
    }

    protected void setProgress(int progress) {
        this.progressBar.setValue(progress);
    }

    protected void setText(String text) {
        this.label.setText(text);
    }
}
