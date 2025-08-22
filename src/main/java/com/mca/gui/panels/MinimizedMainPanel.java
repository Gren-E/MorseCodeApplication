package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.JComponentFactory;
import com.mca.gui.window.AppWindow;
import com.mca.localizer.LocalizerAttribute;
import com.util.gui.GBC;
import com.util.gui.component.OvalFramedButton;
import com.util.gui.component.OvalFramedPanel;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinimizedMainPanel extends MainPanel{

    private final JPanel centerCardPanel;
    private final JPanel consolePanel;

    private final OvalFramedButton translationButton;
    private final OvalFramedButton settingsButton;
    private final OvalFramedButton dragButton;

    private final CardLayout cardLayout;

    public MinimizedMainPanel(AppWindow parent) {
        super(parent);
        setOpaque(false);

        centerCardPanel = new JPanel();
        centerCardPanel.setOpaque(false);
        cardLayout = new CardLayout();
        centerCardPanel.setLayout(cardLayout);

        translationButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.MORSE_TRANSLATION);
        settingsButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.SETTINGS);
        dragButton = JComponentFactory.createMenuOvalButton("✣");

        consolePanel = new JPanel();
        consolePanel.setOpaque(false);
        consolePanel.setLayout(new GridLayout(2,1));
        consolePanel.add(translationButton);
        consolePanel.add(settingsButton);
    }

    @Override
    public void updateLayout() {
        OvalFramedPanel minimizedPanel = new OvalFramedPanel();
        setLayout(new BorderLayout());
        add(minimizedPanel, BorderLayout.CENTER);

        minimizedPanel.setBackground(AppColorPalette.MAIN_PANEL_BACKGROUND);

        minimizedPanel.setLayout(new GridBagLayout());
        minimizedPanel.add(dragButton, new GBC(0,0).setInsets(0,0,0,0).setFill(GBC.HORIZONTAL).setInsets(10,30,10,10));
        minimizedPanel.add(centerCardPanel, new GBC(1,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(10,10,10,10));
        minimizedPanel.add(consolePanel, new GBC(2,0).setInsets(0,0,0,0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,30));

        JPanel console = new JPanel(new GridBagLayout());
        console.setOpaque(false);
        console.add(settingsPanel, new GBC(0,0).setInsets(5).setWeight(1,1).setFill(GBC.BOTH));
        console.add(authorsLabel, new GBC(0,1));

        centerCardPanel.add("MORSE_TRANSLATION", window.getMorseTranslationPanel());
        centerCardPanel.add("CONSOLE", console);

        translationButton.setActionListener(e -> {
            cardLayout.show(centerCardPanel, "MORSE_TRANSLATION");
            refreshModeButtons(true);
        });
        settingsButton.setActionListener(e -> {
            cardLayout.show(centerCardPanel, "CONSOLE");
            refreshModeButtons(false);
        });

        refreshModeButtons(true);

        MouseAdapter dragButtonMouseAdapter = new MouseAdapter() {
            private int xDiff;
            private int yDiff;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                xDiff = e.getXOnScreen() - window.getX();
                yDiff = e.getYOnScreen() - window.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                window.setLocation(e.getXOnScreen() - xDiff, e.getYOnScreen() - yDiff);
            }
        };

        dragButton.addMouseListener(dragButtonMouseAdapter);
        dragButton.addMouseMotionListener(dragButtonMouseAdapter);
    }

    public void refreshModeButtons(boolean isTranslatorShowing) {
        translationButton.setEnabled(!isTranslatorShowing);
        settingsButton.setEnabled(isTranslatorShowing);
    }

    @Override
    public void removeAll() {
        super.removeAll();
        centerCardPanel.removeAll();
    }

}
