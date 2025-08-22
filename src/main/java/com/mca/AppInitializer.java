package com.mca;

import com.mca.gui.window.AppWindow;

import java.awt.EventQueue;

public class AppInitializer {

    public static void main(String[] args) {

        AppEnv.setGraphicsEnvironment();

        EventQueue.invokeLater(() -> {
           AppWindow window = new AppWindow();
        });
    }
}
