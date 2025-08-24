package com.mca.localizer;

import com.util.gui.component.OvalButton;

public class LocalizedOvalButton extends OvalButton {

    public LocalizedOvalButton(LocalizerAttribute attribute) {
        setButtonText(Localizer.get(attribute));
        Localizer.addLocaleListener(attribute, this::setButtonText);
    }
}
