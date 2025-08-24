package com.mca.gui;

import com.mca.localizer.LocalizedOvalButton;
import com.mca.localizer.LocalizerAttribute;
import com.util.gui.component.OvalButton;
import com.util.gui.component.RectangularJTextArea;
import com.util.gui.component.SimpleScrollBarUI;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.function.Consumer;

public class JComponentFactory {

    public static OvalButton createMenuOvalButton(String buttonText) {
        OvalButton button = new OvalButton(buttonText);
        formatButton(button, AppColorPalette.ELEMENT_BACKGROUND, AppColorPalette.ELEMENT_HIGHLIGHT, AppColorPalette.ELEMENT_FOREGROUND);
        return button;
    }

    public static OvalButton createTranslationOvalButton(String buttonText) {
        OvalButton button = new OvalButton(buttonText);
        formatButton(button, AppColorPalette.SPECIAL_ELEMENT_BACKGROUND, AppColorPalette.SPECIAL_ELEMENT_HIGHLIGHT, AppColorPalette.SPECIAL_ELEMENT_FOREGROUND);
        return button;
    }

    public static OvalButton createLocalizedOvalButton(LocalizerAttribute attribute) {
        OvalButton button = new LocalizedOvalButton(attribute);
        formatButton(button, AppColorPalette.ELEMENT_BACKGROUND, AppColorPalette.ELEMENT_HIGHLIGHT, AppColorPalette.ELEMENT_FOREGROUND);
        return button;
    }

    public static JTextArea createJTextArea(Consumer<DocumentEvent> action, int fontSize) {
        RectangularJTextArea area = new RectangularJTextArea();
        area.setArch(20);
        area.setFont(new Font("Cambria", Font.PLAIN, fontSize));
        area.setForeground(AppColorPalette.TEXT_AREA_FOREGROUND);
        area.addDocumentAction(action);
        area.forceUpperCase();
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

    private static void formatButton(OvalButton button, Color background, Color highlight, Color foreground) {
        button.enableBorder(true);
        button.setBackgroundColor(background);
        button.setHighlightColor(highlight);
        button.setForeground(foreground);
    }

}
