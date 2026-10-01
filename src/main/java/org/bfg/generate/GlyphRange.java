package org.bfg.generate;

/**
 * The range of characters to be included in a {@link BitmapFont}.
 */
public final class GlyphRange {

    /** First character in the range, (inclusive). */
    public final char lowEnd;

    /** Last character in the range (inclusive). */
    public final char highEnd;

    /**
     * Creates a range from {@code '\\u0000'} through {@code highEnd}, both inclusive.
     *
     * @param highEnd The last inclusive character in the range.
     * @throws IllegalArgumentException If {@code highEnd} is less than {@code 0}.
     */
    public GlyphRange(char highEnd) {
        this((char) 0, highEnd);
    }

    /**
     * Creates a range from {@code '\\u0000'} through {@code highEnd}, both inclusive.
     *
     * @param lowEnd  The first inclusive character in the range.
     * @param highEnd The last inclusive character in the range.
     * @throws IllegalArgumentException If {@code highEnd} is less than {@code lowEnd}.
     */
    public GlyphRange(char lowEnd, char highEnd) {
        if (lowEnd > highEnd)
            throw new IllegalArgumentException("Low end must be less than high end");

        this.lowEnd = lowEnd;
        this.highEnd = highEnd;
    }

    /**
     * Return if the given character is contained in this range.
     *
     * @param c The character to look for.
     * @return If the character is contained in this range.
     */
    public boolean contains(char c) {
        return c >= lowEnd && c <= highEnd;
    }

    /**
     * Returns the total number of characters in this range.
     *
     * @return The total number of characters in this range.
     */
    public int getCount() {
        return highEnd - lowEnd + 1;
    }
}
