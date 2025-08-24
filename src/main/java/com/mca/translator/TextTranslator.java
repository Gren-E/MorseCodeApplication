package com.mca.translator;

import java.util.ArrayList;
import java.util.List;

public class TextTranslator {

    public static MorseSymbol[] translateToMorse(String text) {
        List<MorseSymbol> morseTranscription = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            MorseSymbol symbol = MorseSymbol.of(text.charAt(i));
            if (symbol != null) {
                morseTranscription.add(symbol);
            }
        }
        return morseTranscription.toArray(new MorseSymbol[0]);
    }

    public static String translateToText(MorseSymbol[] morseSymbols) {
        StringBuilder translatedText = new StringBuilder();
        for (MorseSymbol symbol : morseSymbols) {
            translatedText.append(symbol.getLatin());
        }
        return translatedText.toString();
    }

}
