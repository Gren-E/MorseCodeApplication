package com.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AudioPlayerTest {

    @Test
    public void audioTest() {
        int SOUND_DURATION_IN_SECONDS = 3;

        long start = System.currentTimeMillis();
        boolean result = AudioPlayer.playSound(SOUND_DURATION_IN_SECONDS);
        Assertions.assertTrue(result);

        long end = System.currentTimeMillis();
        Assertions.assertTrue(end - start > SOUND_DURATION_IN_SECONDS * 1000, "Sound length was not long enough.");
        Assertions.assertTrue(end - start < (SOUND_DURATION_IN_SECONDS + 0.5) * 1000, "Sound length was too long.");
    }

}
