package com.util.gui.component;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Consumer;

public class RectangularJTextArea extends JTextArea {

    private int archValue;

    public RectangularJTextArea() {
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public void setArch(int archValue) {
        this.archValue = archValue;
    }

    public void addDocumentAction(Consumer<DocumentEvent> action) {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                action.accept(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                action.accept(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                action.accept(e);
            }
        });
    }

    public void forceUpperCase() {
        ((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
                super.insertString(fb, offset, string.toUpperCase(), attr);
            }

            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                super.replace(fb, offset, length, text.toUpperCase(), attrs);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Color color = getBackground();

        g.setColor(color);
        g.fillRoundRect(0, 0, width, height, archValue, archValue);

        ui.paint(g, this);
    }

}
