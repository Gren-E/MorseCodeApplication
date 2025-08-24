package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.JComponentFactory;
import com.mca.gui.window.AppWindow;
import com.mca.localizer.LocalizedJLabel;
import com.mca.localizer.Localizer;
import com.mca.localizer.LocalizerAttribute;
import com.util.gui.GBC;
import com.util.gui.component.OvalButton;
import com.util.gui.component.RectangularPanel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.util.Locale;

public class SettingsPanel extends RectangularPanel {

    private final AppWindow window;

    private final OvalButton maximizedModeButton;
    private final OvalButton minimizedModeButton;
    private final OvalButton plLanguageButton;
    private final OvalButton enLanguageButton;
    private final OvalButton exitButton;

    public SettingsPanel(AppWindow parent) {
        window = parent;
        setOpaque(false);
        setArch(20);

        JLabel screenModeLabel = new LocalizedJLabel(LocalizerAttribute.WINDOW_MODE, SwingConstants.CENTER);
        screenModeLabel.setForeground(AppColorPalette.ELEMENT_FOREGROUND);

        JLabel languageModeLabel = new LocalizedJLabel(LocalizerAttribute.LANGUAGE, SwingConstants.CENTER);
        languageModeLabel.setForeground(AppColorPalette.ELEMENT_FOREGROUND);

        maximizedModeButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.FULL_SCREEN);
        maximizedModeButton.setActionListener(event -> parent.enableFullscreen(true));

        minimizedModeButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.MINIMIZED);
        minimizedModeButton.setActionListener(event -> parent.enableFullscreen(false));

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
        add(screenModeLabel, new GBC(0, 0, 2, 1).setInsets(5,20,2,20).setFill(GBC.HORIZONTAL));
        add(maximizedModeButton, new GBC(0, 1, 1, 1).setInsets(0,20,0,0).setFill(GBC.HORIZONTAL));
        add(minimizedModeButton, new GBC(1, 1, 1, 1).setInsets(0,0,0,20).setFill(GBC.HORIZONTAL));
        add(languageModeLabel, new GBC(0, 2, 2, 1).setInsets(2,20,2,20).setFill(GBC.HORIZONTAL));
        add(enLanguageButton, new GBC(0, 3, 1, 1).setInsets(0,20,0,0).setFill(GBC.HORIZONTAL));
        add(plLanguageButton, new GBC(1, 3, 1, 1).setInsets(0,0,0,20).setFill(GBC.HORIZONTAL));
        add(exitButton, new GBC(0, 4, 2, 1).setInsets(10, 20, 10, 20).setFill(GBC.HORIZONTAL));
    }

    public void refreshScreenModeButtons() {
        maximizedModeButton.setEnabled(!window.isFullscreen());
        minimizedModeButton.setEnabled(window.isFullscreen());
    }

    public void refreshLanguageButtons() {
        plLanguageButton.setEnabled(!Localizer.isSetToPolish());
        enLanguageButton.setEnabled(!Localizer.isSetToEnglish());
    }

}
