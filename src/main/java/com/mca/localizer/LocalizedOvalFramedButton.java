package com.mca.localizer;

import com.util.gui.component.OvalFramedButton;

public class LocalizedOvalFramedButton extends OvalFramedButton {

    public LocalizedOvalFramedButton(LocalizerAttribute attribute) {
        setButtonText(Localizer.get(attribute));
        Localizer.addLocaleListener(attribute, this::setButtonText);
    }
}
