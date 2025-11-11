package org.bfg.gui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public final class LinkLabel extends JLabel {

    private ActionListener actionListener;

    public LinkLabel(String text) {
        super(text);
        setForeground(new Color(90, 90, 255));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                // TODO Is the ActionEvent right? Probably not
                if (actionListener != null)
                    actionListener.actionPerformed(new ActionEvent(this,
                            ActionEvent.ACTION_PERFORMED, ""));
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

    public void addActionListener(ActionListener actionListener) {
        if (this.actionListener != null)
            throw new IllegalStateException("Can only have one ActionListener");

        this.actionListener = actionListener;
    }
}
