package org.bfg.generate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class MetaDataGenerator {

    public static void exportMetaData(File file, BitmapFont font) throws IOException {
        Objects.requireNonNull(file, "File is null");
        Objects.requireNonNull(font, "Font is null");

        try {
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            final Element fontElement = document.createElement("font");
            fontElement.setAttribute("leading", Integer.toString(font.getLeading()));
            document.appendChild(fontElement);

            final GlyphRange range = font.getRange();
            for (char c = range.lowEnd; c <= range.highEnd; c++) {
                final GlyphInfo glyphInfo = font.getGlyphInfo(c);

                final Element glyphElement = document.createElement("glyph");
                glyphElement.setAttribute("id", Integer.toString(c));
                glyphElement.setAttribute("x", Integer.toString(glyphInfo.x));
                glyphElement.setAttribute("y", Integer.toString(glyphInfo.y));
                glyphElement.setAttribute("width", Integer.toString(glyphInfo.width));
                glyphElement.setAttribute("height", Integer.toString(glyphInfo.height));

                fontElement.appendChild(glyphElement);
            }

            if (!file.exists())
                file.createNewFile();

            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (IOException | ParserConfigurationException | TransformerException exception) {
            throw new IOException(exception);
        }
    }
}
