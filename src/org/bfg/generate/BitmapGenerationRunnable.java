package org.bfg.generate;

import org.bfg.util.work.Work;

import java.awt.*;

public final class BitmapGenerationRunnable implements Work.TaskIndexedRunnable {

    private final BitmapFontGenerator bitmapFontGenerator;

    public BitmapGenerationRunnable(Font font, int charCount) {
        this.bitmapFontGenerator = new BitmapFontGenerator(font, charCount);
    }

    @Override
    public void run(int taskIndex) {
        this.bitmapFontGenerator.generateNext();
    }
}
