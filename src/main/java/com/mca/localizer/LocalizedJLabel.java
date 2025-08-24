package com.mca.localizer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LocalizedJLabel extends JLabel {

    public LocalizedJLabel(LocalizerAttribute attribute) {
        this(attribute, SwingConstants.LEFT);
    }

    public LocalizedJLabel(LocalizerAttribute attribute, int horizontalAlignment) {
        setText(Localizer.get(attribute));
        Localizer.addLocaleListener(attribute, this::setText);
        setHorizontalAlignment(horizontalAlignment);
    }

}
