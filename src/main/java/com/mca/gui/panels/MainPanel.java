package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.window.AppWindow;
import com.mca.localizer.LocalizedJLabel;
import com.mca.localizer.LocalizerAttribute;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class MainPanel extends JPanel {

    protected final AppWindow window;

    protected final SettingsPanel settingsPanel;
    protected final JLabel authorsLabel;

    protected MainPanel(AppWindow parent) {

        window = parent;

        settingsPanel = new SettingsPanel(parent);
        settingsPanel.setBackground(AppColorPalette.FUNCTIONALITY_PANEL_BACKGROUND);

        authorsLabel = new LocalizedJLabel(LocalizerAttribute.AUTHORS);
        authorsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorsLabel.setForeground(AppColorPalette.ELEMENT_FOREGROUND);

    }

    public void refreshPanel() {
        settingsPanel.refreshScreenModeButtons();
        updateLayout();
    }

    public abstract void updateLayout();

}
