package org.bfg.generate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Export {

    public static void export(File imageFile, BitmapFont bitmapFont) throws IOException {
        export(imageFile, new File(changeFileExtension(imageFile.getAbsolutePath(), ".xml")), bitmapFont);
    }

    private static String changeFileExtension(String path, String newExtension) {
        int extensionIndex;
        for(extensionIndex = path.length() - 1; extensionIndex >= 0; extensionIndex--) {
            if (path.charAt(extensionIndex) == '.')
                break;
        }

        return path.substring(0, extensionIndex + 1) + newExtension;
    }

    public static void export(File imageFile, File dataFile, BitmapFont bitmapFont) throws IOException {
        Objects.requireNonNull(imageFile);
        Objects.requireNonNull(dataFile);
        Objects.requireNonNull(bitmapFont);

        ImageIO.write(bitmapFont.getAtlasImage(), "PNG", imageFile);

        try {
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            final Element fontElement = document.createElement("font");
            fontElement.setAttribute("leading", Integer.toString(bitmapFont.getLeading()));
            fontElement.setAttribute("ascent", Integer.toString(bitmapFont.getAscent()));
            fontElement.setAttribute("descent", Integer.toString(bitmapFont.getDescent()));
            document.appendChild(fontElement);

            final GlyphRange range = bitmapFont.getRange();
            for (char c = range.lowEnd; c <= range.highEnd; c++) {
                final GlyphInfo glyphInfo = bitmapFont.getGlyphInfo(c);

                final Element glyphElement = document.createElement("glyph");
                glyphElement.setAttribute("id", Integer.toString(c));
                glyphElement.setAttribute("x", Integer.toString(glyphInfo.x));
                glyphElement.setAttribute("y", Integer.toString(glyphInfo.y));
                glyphElement.setAttribute("width", Integer.toString(glyphInfo.width));
                glyphElement.setAttribute("height", Integer.toString(glyphInfo.height));

                fontElement.appendChild(glyphElement);
            }

            if (!dataFile.exists())
                dataFile.createNewFile();

            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(document), new StreamResult(dataFile));
        } catch (IOException | ParserConfigurationException | TransformerException exception) {
            throw new IOException(exception);
        }
    }
}
