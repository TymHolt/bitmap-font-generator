package org.bfg.generate;

import org.bfg.util.work.Work;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class BitmapGenerationRunnable implements Work.TaskIndexedRunnable {

    private final BitmapFontGenerator bitmapFontGenerator;
    private final MetaDataGenerator metaDataGenerator;

    public BitmapGenerationRunnable(Font font, char charCount) {
        this.bitmapFontGenerator = new BitmapFontGenerator(font, charCount);
        this.metaDataGenerator = new MetaDataGenerator();
    }

    @Override
    public void run(int taskIndex) {
        final char c = (char) taskIndex;
        final Rectangle bounds = this.bitmapFontGenerator.generate(c);
        this.metaDataGenerator.addData(c, bounds);
    }

    public BufferedImage getResultImage() {
        return this.bitmapFontGenerator.getImage();
    }

    public MetaDataGenerator getResultMetaData() {
        return this.metaDataGenerator;
    }

    public void dispose() {
        this.bitmapFontGenerator.dispose();
    }
}
