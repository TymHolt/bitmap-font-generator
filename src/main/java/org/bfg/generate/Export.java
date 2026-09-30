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

/**
 * This class provides a utility function for exporting a {@link BitmapFont} as files.
 */
public final class Export {

    /**
     * Exports a {@link BitmapFont} as two files. An image file containing the rendered atlas and a {@code .xml} file
     * containing the font and glyph metadata. If the files do not exist they will be created. If the files exist they
     * will be overwritten without warning.
     *
     * @param imageFile  The {@code .png} image file for the atlas, must not be {@code null}.
     * @param dataFile   The {@code .xml} file for the metadata, must not be {@code null}.
     * @param bitmapFont The font to export, must not be {@code null}.
     * @param exportUV   If the calculated glyph UV coordinates should be exported alongside the metadata.
     * @throws NullPointerException If a required argument is {@code null}.
     * @throws IOException          If the files cannot be created or written.
     */
    public static void export(File imageFile, File dataFile, BitmapFont bitmapFont, boolean exportUV)
            throws IOException {
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

                if (exportUV) {
                    glyphElement.setAttribute("u", Float.toString(glyphInfo.u));
                    glyphElement.setAttribute("v", Float.toString(glyphInfo.v));
                    glyphElement.setAttribute("uWidth", Float.toString(glyphInfo.uWidth));
                    glyphElement.setAttribute("vHeight", Float.toString(glyphInfo.vHeight));
                }

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
