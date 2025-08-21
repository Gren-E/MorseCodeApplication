package com.mca.localizer;

import javax.swing.JLabel;

public class LocalizedJLabel extends JLabel {

    public LocalizedJLabel(LocalizerAttribute attribute) {
        setText(Localizer.get(attribute));
        Localizer.addLocaleListener(attribute, this::setText);
    }

}
