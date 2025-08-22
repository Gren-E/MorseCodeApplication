package com.mca.localizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Localizer {

    public static final Locale LOCALE_POLISH = new Locale.Builder().setLanguage("pl").build();

    private static final Map<LocalizerAttribute, List<Consumer<String>>> localeChangeListeners = new HashMap<>();

    public synchronized static void setLocale(Locale locale) {
        Locale.setDefault(locale);
        alertLocaleListeners();
    }

    public static String get(LocalizerAttribute localizerAttribute) {
        return get(localizerAttribute.getKey()).orElse(localizerAttribute.getValueIfMissing());
    }

    public static Optional<String> get(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
        if(resourceBundle == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(resourceBundle.getString(key));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static boolean isSetToPolish() {
        return LOCALE_POLISH.getLanguage().equals(Locale.getDefault().getLanguage());
    }

    public static boolean isSetToEnglish() {
        return Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage());
    }

    public synchronized static void addLocaleListener(LocalizerAttribute attribute, Consumer<String> listener) {
        List<Consumer<String>> listeners = localeChangeListeners.computeIfAbsent(attribute, key -> new ArrayList<>());
        listeners.add(listener);
    }

    private synchronized static void alertLocaleListeners() {
        for(Map.Entry<LocalizerAttribute, List<Consumer<String>>> entry : localeChangeListeners.entrySet()) {
            LocalizerAttribute attribute = entry.getKey();
            for(Consumer<String> listener : entry.getValue()) {
                listener.accept(Localizer.get(attribute));
            }
        }
    }
}
