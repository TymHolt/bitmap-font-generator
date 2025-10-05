package org.bfg.generate;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public final class MetaDataGenerator {

    private final StringBuilder stringBuilder;

    public MetaDataGenerator() {
        this.stringBuilder = new StringBuilder();
    }

    public void addData(char c, Rectangle bounds) {
        this.stringBuilder.append((int) c);
        this.stringBuilder.append(" ");
        this.stringBuilder.append(bounds.x);
        this.stringBuilder.append(" ");
        this.stringBuilder.append(bounds.y);
        this.stringBuilder.append(" ");
        this.stringBuilder.append(bounds.width);
        this.stringBuilder.append(" ");
        this.stringBuilder.append(bounds.height);
        this.stringBuilder.append('\n');
    }

    public void addAll(HashMap<Character, Rectangle> charRectMap) {
        for (Character character : charRectMap.keySet())
            addData(character, charRectMap.get(character));
    }

    public void write(File file) throws IOException {
        final String data = getString();
        final FileOutputStream fileOutputStream = new FileOutputStream(file);

        for (int index = 0; index < data.length(); index++)
            fileOutputStream.write((int) data.charAt(index));

        fileOutputStream.close();
    }

    public String getString() {
        return this.stringBuilder.toString();
    }
}
