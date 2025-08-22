package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.JComponentFactory;
import com.mca.gui.window.AppWindow;
import com.mca.localizer.LocalizedJLabel;
import com.mca.localizer.Localizer;
import com.mca.localizer.LocalizerAttribute;
import com.util.gui.GBC;
import com.util.gui.component.OvalFramedButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.Locale;

public class SettingsPanel extends JPanel {

    private final AppWindow window;

    private final OvalFramedButton maximizedModeButton;
    private final OvalFramedButton minimizedModeButton;
    private final OvalFramedButton plLanguageButton;
    private final OvalFramedButton enLanguageButton;
    private final OvalFramedButton exitButton;

    public SettingsPanel(AppWindow parent) {
        window = parent;
        setOpaque(false);

        JLabel screenModeLabel = new LocalizedJLabel(LocalizerAttribute.WINDOW_MODE);
        screenModeLabel.setForeground(AppColorPalette.ELEMENT_FOREGROUND);
        screenModeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel languageModeLabel = new LocalizedJLabel(LocalizerAttribute.LANGUAGE);
        languageModeLabel.setForeground(AppColorPalette.ELEMENT_FOREGROUND);
        languageModeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        maximizedModeButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.FULL_SCREEN);
        maximizedModeButton.setActionListener(event -> parent.toggleFullscreen());

        minimizedModeButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.MINIMIZED);
        minimizedModeButton.setActionListener(event -> parent.toggleFullscreen());

        refreshScreenModeButtons();

        enLanguageButton = JComponentFactory.createMenuOvalButton("EN");
        enLanguageButton.setActionListener(event -> {
            Localizer.setLocale(Locale.ENGLISH);
            refreshLanguageButtons();
        });
        plLanguageButton = JComponentFactory.createMenuOvalButton("PL");
        plLanguageButton.setActionListener(event -> {
            Localizer.setLocale(Localizer.LOCALE_POLISH);
            refreshLanguageButtons();
        });

        refreshLanguageButtons();

        exitButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.EXIT);
        exitButton.setActionListener(event -> System.exit(0));

        setLayout(new GridBagLayout());

        add(screenModeLabel, new GBC(0, 0, 2, 1).setFill(GBC.HORIZONTAL).setInsets(5,20,2,20));
        add(maximizedModeButton, new GBC(0, 1, 1, 1).setFill(GBC.HORIZONTAL).setInsets(0,20,0,0));
        add(minimizedModeButton, new GBC(1, 1, 1, 1).setFill(GBC.HORIZONTAL).setInsets(0,0,0,20));
        add(languageModeLabel, new GBC(0, 2, 2, 1).setFill(GBC.HORIZONTAL).setInsets(2,20,2,20));
        add(enLanguageButton, new GBC(0, 3, 1, 1).setFill(GBC.HORIZONTAL).setInsets(0,20,0,0));
        add(plLanguageButton, new GBC(1, 3, 1, 1).setFill(GBC.HORIZONTAL).setInsets(0,0,0,20));
        add(exitButton, new GBC(0, 4, 2, 1).setFill(GBC.HORIZONTAL).setInsets(10, 20, 10, 20));
    }

    public void refreshScreenModeButtons() {
        maximizedModeButton.setEnabled(!window.isFullscreen());
        minimizedModeButton.setEnabled(window.isFullscreen());
    }

    public void refreshLanguageButtons() {
        plLanguageButton.setEnabled(!Localizer.isSetToPolish());
        enLanguageButton.setEnabled(!Localizer.isSetToEnglish());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Color color = getBackground();

        g.setColor(color);
        g.fillRoundRect(0, 0, width, height, 20, 20);
    }

}
