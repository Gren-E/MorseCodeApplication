package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.window.AppWindow;
import com.util.gui.GBC;
import com.util.gui.component.RectangularPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

public class MaximizedMainPanel extends MainPanel {

    public MaximizedMainPanel(AppWindow parent) {
        super(parent);
        setBackground(AppColorPalette.MAIN_PANEL_OPAQUE_BACKGROUND);
    }

    @Override
    public void updateLayout() {
        RectangularPanel maximizedPanel = new RectangularPanel();
        maximizedPanel.enableBorder(true);

        setLayout(new BorderLayout());
        add(maximizedPanel, BorderLayout.CENTER);

        maximizedPanel.setBackground(AppColorPalette.MAIN_PANEL_BACKGROUND);

        maximizedPanel.setLayout(new GridBagLayout());
        maximizedPanel.add(settingsPanel, new GBC(0,0).setInsets(40,40,20,5).setFill(GBC.VERTICAL));
        maximizedPanel.add(window.getMorseTranslationPanel(), new GBC(1,0).setInsets(40,5,20,40).setWeight(1,1).setFill(GBC.BOTH));
        maximizedPanel.add(authorsLabel, new GBC(0,1,2,1).setInsets(5,5,20,5));
    }

}
