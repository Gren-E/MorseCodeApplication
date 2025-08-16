package com.mca.translator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MorseSymbol {

    private static final Map<boolean[], MorseSymbol> symbols = new HashMap<>();

    private boolean[] transcription;

    public synchronized static MorseSymbol of(boolean... booleans) {
        MorseSymbol existingSymbol = MorseSymbol.getExistingSymbol(booleans);
        if (existingSymbol != null) {
            return existingSymbol;
        }

        MorseSymbol symbol = new MorseSymbol();
        symbol.setTranscription(booleans);
        symbols.put(booleans, symbol);
        return symbol;
    }

    public synchronized static MorseSymbol of(char charSymbol) {
        if (charSymbol >= 'a' && charSymbol <= 'z') {
            charSymbol -= 'a' - 'A';
        }
        boolean[] morseCode = MorseDictionary.getMorseCode(charSymbol);
        if (morseCode == null) {
            return null;
        }
        return MorseSymbol.of(morseCode);
    }

    public String getLatin() {
        return String.valueOf(MorseDictionary.getChar(transcription));
    }

    private static MorseSymbol getExistingSymbol(boolean... booleans) {
        for (boolean[] key : symbols.keySet()) {
            if (Arrays.equals(key, booleans)) {
                return symbols.get(key);
            }
        }
        return null;
    }

    private void setTranscription(boolean[] booleans) {
        this.transcription = booleans;
    }

    public boolean[] getTranscription() {
        return this.transcription;
    }

}
