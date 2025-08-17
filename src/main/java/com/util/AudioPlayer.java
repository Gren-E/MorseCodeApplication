package com.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {

    public static boolean playSound(double soundDurationInSeconds) {
        try {
            float sampleRate = 40000;

            AudioFormat audioFormat = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
            sourceDataLine.open();
            sourceDataLine.start();

            byte[] buffer = new byte[1];
            for (int i = 0; i < soundDurationInSeconds * sampleRate; i++) {
                double angle = i / 50.0 * Math.PI;
                buffer[0] = (byte) (Math.sin(angle) * 100);
                sourceDataLine.write(buffer, 0, 1);
            }

            sourceDataLine.drain();
            sourceDataLine.stop();
            sourceDataLine.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
