package org.bfg.generate;

public final class GlyphRange {

    public final char lowEnd;
    public final char highEnd;

    public GlyphRange(char highEnd) {
        this((char) 0, highEnd);
    }

    public GlyphRange(char lowEnd, char highEnd) {
        if (lowEnd > highEnd)
            throw new IllegalArgumentException("Low end must be less than high end");

        this.lowEnd = lowEnd;
        this.highEnd = highEnd;
    }

    public boolean contains(char c) {
        return c >= lowEnd && c <= highEnd;
    }

    public int getCount() {
        return highEnd - lowEnd + 1;
    }
}
