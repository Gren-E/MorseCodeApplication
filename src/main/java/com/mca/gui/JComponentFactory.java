package com.mca.gui;

import com.mca.localizer.LocalizedOvalFramedButton;
import com.mca.localizer.LocalizerAttribute;
import com.util.gui.component.OvalFramedButton;
import com.util.gui.component.SimpleScrollBarUI;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.function.Consumer;

public class JComponentFactory {

    public static OvalFramedButton createMenuOvalButton(String buttonText) {
        OvalFramedButton button = new OvalFramedButton(buttonText);
        button.setBackgroundColor(AppColorPalette.ELEMENT_BACKGROUND);
        button.setForeground(AppColorPalette.ELEMENT_FOREGROUND);
        button.setHighlightColor(AppColorPalette.ELEMENT_HIGHLIGHT);
        return button;
    }

    public static OvalFramedButton createTranslationOvalButton(String buttonText) {
        OvalFramedButton button = new OvalFramedButton(buttonText);
        button.setBackgroundColor(AppColorPalette.SPECIAL_ELEMENT_BACKGROUND);
        button.setForeground(AppColorPalette.SPECIAL_ELEMENT_FOREGROUND);
        button.setHighlightColor(AppColorPalette.SPECIAL_ELEMENT_HIGHLIGHT);
        return button;
    }

    public static OvalFramedButton createLocalizedOvalButton(LocalizerAttribute attribute) {
        OvalFramedButton button = new LocalizedOvalFramedButton(attribute);
        button.setBackgroundColor(AppColorPalette.ELEMENT_BACKGROUND);
        button.setForeground(AppColorPalette.ELEMENT_FOREGROUND);
        button.setHighlightColor(AppColorPalette.ELEMENT_HIGHLIGHT);
        return button;
    }

    public static JTextArea createJTextArea(Consumer<DocumentEvent> action, int fontSize) {
        JTextArea area = new JTextArea() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();

                Color color = getBackground();

                g.setColor(color);
                g.fillRoundRect(0, 0, width, height, 20, 20);

                ui.paint(g, this);
            }
        };
        ((AbstractDocument) area.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
                super.insertString(fb, offset, string.toUpperCase(), attr);
            }

            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                super.replace(fb, offset, length, text.toUpperCase(), attrs);
            }
        });
        area.setOpaque(false);
        area.setFont(new Font("Cambria", Font.PLAIN, fontSize));
        area.setForeground(AppColorPalette.TEXT_AREA_FOREGROUND);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.getDocument().addDocumentListener(new DocumentListener() {
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

        return area;
    }

    public static JScrollPane createTransparentScrollPane(JComponent component, int verticalScrollbarMode, int horizontalScrollbarMode, int unitIncrement) {
        JScrollPane scrollPane = new JScrollPane(component, verticalScrollbarMode, horizontalScrollbarMode);
        scrollPane.getVerticalScrollBar().setUI(new SimpleScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(unitIncrement);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setBackground(AppColorPalette.TRANSPARENCY);
        scrollPane.getVerticalScrollBar().setForeground(AppColorPalette.ELEMENT_BACKGROUND);
        return scrollPane;
    }

}
