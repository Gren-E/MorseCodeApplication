package com.mca.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SymbolTranslatorTest {

    @Test
    public void morseSymbolTest() {
        MorseSymbol symbol1 = MorseSymbol.of(false, true);
        Assertions.assertNotNull(symbol1);
        Assertions.assertFalse(symbol1.getTranscription()[0]);
        Assertions.assertTrue(symbol1.getTranscription()[1]);
        Assertions.assertEquals(2, symbol1.getTranscription().length);

        MorseSymbol symbol2 = MorseSymbol.of(false, true);
        Assertions.assertSame(symbol1, symbol2);
        Assertions.assertEquals(symbol1, symbol2);

        MorseSymbol symbol3 = MorseSymbol.of(true, false);
        Assertions.assertNotSame(symbol2, symbol3);
        Assertions.assertNotEquals(symbol2, symbol3);
    }

    @Test
    public void threadSafetyMorseSymbolTest() {
        AtomicBoolean exceptionThrown = new AtomicBoolean(false);
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                try {
                    MorseSymbol.of(true, true, true);
                } catch (Exception e) {
                    exceptionThrown.set(true);
                    throw e;
                }
            }).start();
        }
        Assertions.assertFalse(exceptionThrown.get());
    }

    @ParameterizedTest
    @MethodSource("symbolTranslatorTestParameters")
    public void symbolTranslatorTest(boolean[] expectedResult, char charSymbol) {
        MorseSymbol symbol = MorseSymbol.of(charSymbol);
        if (!MorseDictionary.isSymbolSupported(charSymbol)) {
            Assertions.assertNull(symbol);
            return;
        }
        Assertions.assertNotNull(symbol);
        Assertions.assertArrayEquals(expectedResult, symbol.getTranscription());
    }

    private static Object[] symbolTranslatorTestParameters() {
        return new Object[] {
                Arguments.of(new boolean[] {false}, 'e'),
                Arguments.of(new boolean[] {false, true}, 'A'),
                Arguments.of(null, '§'),
                Arguments.of(new boolean[] {}, ' ')
        };
    }

    @Test
    public void symbolCaseTest() {
        MorseSymbol symbolUpperCaseA = MorseSymbol.of('A');
        MorseSymbol symbolLowerCaseA = MorseSymbol.of('a');
        Assertions.assertEquals(symbolUpperCaseA, symbolLowerCaseA);
    }

    @Test
    public void textTranslatorTest() {
        String text = "Test";
        MorseSymbol[] morseTranscription = TextTranslator.translateToMorse(text);
        Assertions.assertNotNull(morseTranscription);

        MorseSymbol[] expectedResult = new MorseSymbol[] {
                MorseSymbol.of(true),
                MorseSymbol.of(false),
                MorseSymbol.of(false, false, false),
                MorseSymbol.of(true)
        };
        Assertions.assertArrayEquals(expectedResult, morseTranscription);

        String translatedText = TextTranslator.translateToText(expectedResult);
        Assertions.assertEquals(text.toUpperCase(), translatedText);
    }

}
