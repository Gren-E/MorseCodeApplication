package com.mca;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class AppEnv {

    private static GraphicsEnvironment ge;

    public static void setGraphicsEnvironment() {
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    }

    public static GraphicsDevice getDefaultScreen() {
        return ge.getDefaultScreenDevice();
    }

}
