package org.bfg.gui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * A component based on {@link JLabel}, which is styled like a hyperlink and supports an {@code onClick} event.
 */
public final class LinkLabel extends JLabel {

    private Runnable onClick = new Runnable() {
        @Override
        public void run() {}
    };

    /**
     * Creates the component with the given display text.
     *
     * @param text The text to display.
     */
    public LinkLabel(String text) {
        super(text);
        setForeground(new Color(90, 90, 255));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onClick.run();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    /**
     * Sets a {@link Runnable} for handling a click event.
     *
     * @param runnable The runnable to execute when clicked, must not be {@code null}.
     */
    public void setOnClick(Runnable runnable) {
        Objects.requireNonNull(runnable);
        this.onClick = runnable;
    }
}
