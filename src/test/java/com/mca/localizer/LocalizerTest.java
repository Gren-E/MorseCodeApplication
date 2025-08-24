package com.mca.localizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class LocalizerTest {

    private static final Locale LOCALE_EN = Locale.ENGLISH;
    private static final Locale LOCALE_PL = new Locale("pl");
    private static final Locale LOCALE_UNSUPPORTED = Locale.FRENCH;

    @Test
    public void localeTest() {
        Localizer.setLocale(LOCALE_EN);
        Assertions.assertTrue(Localizer.isSetToEnglish());
        Assertions.assertFalse(Localizer.isSetToPolish());
        Assertions.assertEquals("Exit", Localizer.get(LocalizerAttribute.EXIT));

        Localizer.setLocale(LOCALE_PL);
        Assertions.assertTrue(Localizer.isSetToPolish());
        Assertions.assertFalse(Localizer.isSetToEnglish());
        Assertions.assertEquals("Wyjście", Localizer.get(LocalizerAttribute.EXIT));

        Localizer.setLocale(LOCALE_UNSUPPORTED);
        Assertions.assertFalse(Localizer.isSetToEnglish());
        Assertions.assertFalse(Localizer.isSetToPolish());
        Assertions.assertEquals("Exit", Localizer.get(LocalizerAttribute.EXIT));
    }

    @Test
    public void componentTest() {
        Localizer.setLocale(LOCALE_EN);

        LocalizedJLabel localizedLabel = new LocalizedJLabel(LocalizerAttribute.SETTINGS);
        Assertions.assertEquals("Settings", localizedLabel.getText());

        Localizer.setLocale(LOCALE_PL);
        Assertions.assertEquals("Opcje", localizedLabel.getText());

        Localizer.setLocale(LOCALE_UNSUPPORTED);
        Assertions.assertEquals("Settings", localizedLabel.getText());
    }

}
