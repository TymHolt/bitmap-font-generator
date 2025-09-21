package org.bfg.generate;

import org.bfg.util.work.Work;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class BitmapGenerationRunnable implements Work.TaskIndexedRunnable {

    private final BitmapFontGenerator bitmapFontGenerator;

    public BitmapGenerationRunnable(Font font, char charCount) {
        this.bitmapFontGenerator = new BitmapFontGenerator(font, charCount);
    }

    @Override
    public void run(int taskIndex) {
        this.bitmapFontGenerator.generate((char) taskIndex);
    }

    public BufferedImage getResult() {
        return this.bitmapFontGenerator.getImage();
    }

    public void dispose() {
        this.bitmapFontGenerator.dispose();
    }
}
