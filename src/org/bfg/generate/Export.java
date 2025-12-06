package org.bfg.generate;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Export {

    public static void export(File imageFile, BitmapFont bitmapFont) throws IOException {
        Objects.requireNonNull(imageFile, "Image file is null");
        Objects.requireNonNull(bitmapFont, "Bitmap font is null");

        if (!imageFile.getAbsolutePath().endsWith(".png"))
            imageFile = new File(imageFile.getAbsolutePath() + ".png");

        final String dataFilePath = changeFileExtension(imageFile.getAbsolutePath(), "xml");
        final File dataFile = new File(dataFilePath);

        ImageIO.write(bitmapFont.getAtlasImage(), "PNG", imageFile);
        MetaDataGenerator.exportMetaData(dataFile, bitmapFont);
    }

    private static String changeFileExtension(String path, String newExtension) {
        int extensionIndex;
        for(extensionIndex = path.length() - 1; extensionIndex >= 0; extensionIndex--) {
            if (path.charAt(extensionIndex) == '.')
                break;
        }

        return path.substring(0, extensionIndex + 1) + newExtension;
    }
}
