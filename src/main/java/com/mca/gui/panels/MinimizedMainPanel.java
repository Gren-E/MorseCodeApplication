package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.JComponentFactory;
import com.mca.gui.window.AppWindow;
import com.mca.localizer.LocalizerAttribute;
import com.util.gui.GBC;
import com.util.gui.component.OvalButton;
import com.util.gui.component.OvalPanel;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinimizedMainPanel extends MainPanel {

    private final JPanel centerCardPanel;
    private final JPanel consolePanel;

    private final OvalButton translationButton;
    private final OvalButton settingsButton;
    private final OvalButton dragButton;

    private final CardLayout cardLayout;

    public MinimizedMainPanel(AppWindow parent) {
        super(parent);
        setOpaque(false);

        cardLayout = new CardLayout();

        centerCardPanel = new JPanel(cardLayout);
        centerCardPanel.setOpaque(false);

        consolePanel = new JPanel(new GridLayout(2,1));
        consolePanel.setOpaque(false);

        translationButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.MORSE_TRANSLATION);
        translationButton.setActionListener(e -> cardLayout.show(centerCardPanel, "MORSE_TRANSLATION"));

        settingsButton = JComponentFactory.createLocalizedOvalButton(LocalizerAttribute.SETTINGS);
        settingsButton.setActionListener(e -> cardLayout.show(centerCardPanel, "CONSOLE"));

        MouseAdapter dragButtonMouseAdapter = new DragMouseAdapter();
        dragButton = JComponentFactory.createMenuOvalButton("✣");
        dragButton.addMouseListener(dragButtonMouseAdapter);
        dragButton.addMouseMotionListener(dragButtonMouseAdapter);
    }

    @Override
    public void updateLayout() {
        consolePanel.add(translationButton);
        consolePanel.add(settingsButton);

        OvalPanel minimizedPanel = new OvalPanel(new GridBagLayout());
        minimizedPanel.enableBorder(true);
        minimizedPanel.setBackground(AppColorPalette.MAIN_PANEL_BACKGROUND);
        minimizedPanel.add(dragButton, new GBC(0,0).setInsets(10,30,10,10).setFill(GBC.HORIZONTAL));
        minimizedPanel.add(centerCardPanel, new GBC(1,0).setInsets(10,10,10,10).setWeight(1,1).setFill(GBC.BOTH));
        minimizedPanel.add(consolePanel, new GBC(2,0).setInsets(10,10,10,30).setFill(GBC.HORIZONTAL));

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

        MouseAdapter dragButtonMouseAdapter = new DragMouseAdapter();

        dragButton.addMouseListener(dragButtonMouseAdapter);
        dragButton.addMouseMotionListener(dragButtonMouseAdapter);

        setLayout(new BorderLayout());
        add(minimizedPanel, BorderLayout.CENTER);
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

    class DragMouseAdapter extends MouseAdapter {
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
    }

}
