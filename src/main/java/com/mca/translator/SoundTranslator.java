package com.mca.translator;

import com.util.AudioPlayer;

public class SoundTranslator {

    public static boolean playSound(MorseSymbol[] symbols) {
        for(MorseSymbol symbol : symbols) {
            boolean result = playSound(symbol);
            if(!result) {
                return false;
            }
            result = await(0.2);
            if(!result) {
                return false;
            }
        }

        return true;
    }

    public static boolean playSound(MorseSymbol symbol) {
        boolean[] transcription = symbol.getTranscription();
        for(boolean signalIsLong : transcription) {
            AudioPlayer.playSound(signalIsLong ? 0.5 : 0.2);
        }

        return await(0.5);
    }

    private static boolean await(double durationInSeconds) {
        try {
            Thread.sleep((int) (durationInSeconds * 1000));
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

}
