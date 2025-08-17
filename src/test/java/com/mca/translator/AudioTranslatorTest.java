package com.mca.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AudioTranslatorTest {

    @Test
    public void translateAudioTest() {
        String text = "cat";
        MorseSymbol[] symbols = TextTranslator.translateToMorse(text);
        boolean result = SoundTranslator.playSound(symbols);
        Assertions.assertTrue(result);
    }

}
