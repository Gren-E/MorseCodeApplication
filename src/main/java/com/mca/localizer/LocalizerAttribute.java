package com.mca.localizer;

public enum LocalizerAttribute {

    EXIT("exit", "N/A_EXIT"),
    SETTINGS("settings", "N/A_SETTINGS"),
    WINDOW_MODE("windowMode", "N/A_WINDOW_MODE"),
    LANGUAGE("language", "N/A_LANGUAGE"),
    FULL_SCREEN("fullScreen", "N/A_FULL_SCREEN"),
    MINIMIZED("minimized", "N/A_MINIMIZED"),
    MORSE_TRANSLATION("morseTranslation", "N/A_MORSE_TRANSLATION"),
    AUTHORS("authors", "N/A_AUTHORS");

    private final String key;
    private final String valueIfMissing;

    private LocalizerAttribute(String key, String valueIfMissing) {
        this.key = key;
        this.valueIfMissing = valueIfMissing;
    }

    public String getKey() {
        return key;
    }

    public String getValueIfMissing() {
        return valueIfMissing;
    }

}
