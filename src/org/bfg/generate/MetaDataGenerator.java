package org.bfg.generate;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public final class MetaDataGenerator {

    public static void exportMetaData(File file, BitmapFont font) throws IOException {
        Objects.requireNonNull(file, "File is null");
        Objects.requireNonNull(font, "Font is null");

        if (!file.exists())
            file.createNewFile();

        final FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("<font leading=\"" + font.getLeading() +"\">\n");

        final GlyphRange range = font.getRange();
        for (char c = range.lowEnd; c <= range.highEnd; c++) {
            final GlyphInfo glyphInfo = font.getGlyphInfo(c);
            fileWriter.write("    <glyph");
            fileWriter.write(" id=\"" + ((int) c) + "\"");
            fileWriter.write(" x=\"" + glyphInfo.x + "\"");
            fileWriter.write(" y=\"" + glyphInfo.y + "\"");
            fileWriter.write(" width=\"" + glyphInfo.width + "\"");
            fileWriter.write(" height=\"" + glyphInfo.height + "\"");
            fileWriter.write("/>\n");
        }

        fileWriter.write("</font>\n");
        fileWriter.close();
    }
}
