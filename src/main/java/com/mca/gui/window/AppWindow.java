package com.mca.gui.window;

import com.mca.AppEnv;
import com.mca.gui.AppColorPalette;
import com.mca.gui.panels.MaximizedMainPanel;
import com.mca.gui.panels.MinimizedMainPanel;
import com.mca.gui.panels.MorseTranslationPanel;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Rectangle;

public class AppWindow extends JFrame {

    private final MorseTranslationPanel morseTranslationPanel;

    private final MinimizedMainPanel minimizedMainPanel;
    private final MaximizedMainPanel maximizedMainPanel;

    private boolean isFullscreen;

    public AppWindow() {
        setTitle("MorseCodeApplication");

        setUndecorated(true);
        setBackground(AppColorPalette.TRANSPARENCY);

        morseTranslationPanel = new MorseTranslationPanel();
        morseTranslationPanel.setBackground(AppColorPalette.FUNCTIONALITY_PANEL_BACKGROUND);

        minimizedMainPanel = new MinimizedMainPanel(this);
        maximizedMainPanel = new MaximizedMainPanel(this);

        setLayout(new BorderLayout());

        enableFullscreen(true);
    }

    public void enableFullscreen(boolean isEnabled) {
        dispose();
        this.isFullscreen = isEnabled;

        if (!this.isFullscreen) {
            setAlwaysOnTop(true);
            setSize(800, 185);
            setExtendedState(JFrame.NORMAL);

            Rectangle windowBounds = AppEnv.getDefaultScreen().getDefaultConfiguration().getBounds();
            int x = (int) windowBounds.getMaxX() / 2 - getWidth() / 2;
            int y = 0;
            setLocation(x, y);

            remove(maximizedMainPanel);
            add(minimizedMainPanel, BorderLayout.CENTER);
            minimizedMainPanel.refreshPanel();

        } else {
            setAlwaysOnTop(false);
            setSize(getMaximumSize());
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            remove(minimizedMainPanel);
            add(maximizedMainPanel, BorderLayout.CENTER);
            maximizedMainPanel.refreshPanel();
        }

        setVisible(true);
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public MorseTranslationPanel getMorseTranslationPanel() {
        return morseTranslationPanel;
    }

    public void remove(JComponent component) {
        super.remove(component);
        component.removeAll();
    }

}
