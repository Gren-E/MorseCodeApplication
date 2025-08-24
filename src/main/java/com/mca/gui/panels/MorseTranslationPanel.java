package com.mca.gui.panels;

import com.mca.gui.AppColorPalette;
import com.mca.gui.JComponentFactory;
import com.mca.translator.MorseDictionary;
import com.mca.translator.MorseSymbol;
import com.mca.translator.SoundTranslator;
import com.mca.translator.TextTranslator;
import com.util.gui.GBC;
import com.util.gui.component.OvalButton;
import com.util.gui.component.RectangularPanel;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.mca.gui.MorseGUIDefaults.DASH;
import static com.mca.gui.MorseGUIDefaults.DELETE;
import static com.mca.gui.MorseGUIDefaults.DOT;
import static com.mca.gui.MorseGUIDefaults.SLASH;
import static com.mca.gui.MorseGUIDefaults.SPACE;
import static com.mca.gui.MorseGUIDefaults.UNDERLINE;

public class MorseTranslationPanel extends RectangularPanel {

    private final JTextArea sourceTextArea;
    private final JTextArea morseCodeTranslationArea;

    private final OvalButton playSoundButton;
    private final OvalButton symbolButton;
    private final OvalButton spaceButton;
    private final OvalButton deleteButton;

    private boolean isSourceTextEnabled;

    public MorseTranslationPanel() {
        setOpaque(false);
        setArch(20);

        sourceTextArea = JComponentFactory.createJTextArea(this::updateTranscription, 18);
        sourceTextArea.addMouseListener(new SwitchTextAreaMouseAdapter(true));

        morseCodeTranslationArea = JComponentFactory.createJTextArea(this::updateText, 22);
        morseCodeTranslationArea.setEditable(false);
        morseCodeTranslationArea.setCaretColor(AppColorPalette.TRANSPARENCY);
        morseCodeTranslationArea.addMouseListener(new SwitchTextAreaMouseAdapter(false));

        playSoundButton = JComponentFactory.createMenuOvalButton("⏵|⏸");
        playSoundButton.setActionListener(action -> {
            String morseText = sourceTextArea.getText();
            SoundTranslator.playSound(TextTranslator.translateToMorse(morseText));
        });

        symbolButton = JComponentFactory.createTranslationOvalButton(String.format("%s / %s", DOT, DASH));
        symbolButton.addMouseListener(new MorseInputMouseAdapter(DOT, DASH));

        spaceButton = JComponentFactory.createTranslationOvalButton(UNDERLINE);
        spaceButton.addMouseListener(new MorseInputMouseAdapter(SPACE, SLASH));

        deleteButton = JComponentFactory.createTranslationOvalButton(DELETE);
        deleteButton.setActionListener(action -> {
            String morseText = morseCodeTranslationArea.getText();
            if (!morseText.isEmpty()) {
                morseCodeTranslationArea.setText(morseText.substring(0, morseText.length() - 1));
            }
        });

        switchToSourceTextArea(true);
        initLayout();
    }

    private void initLayout() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(playSoundButton, new GBC(0,0));
        buttonPanel.add(symbolButton, new GBC(1, 0).setAnchor(GBC.EAST).setWeight(1, 0).setInsets(0, 0, 0, 2));
        buttonPanel.add(spaceButton, new GBC(2, 0).setAnchor(GBC.WEST).setWeight(1, 0).setInsets(0, 2, 0, 0));
        buttonPanel.add(deleteButton, new GBC(3, 0).setAnchor(GBC.EAST).setWeight(0, 0));

        JScrollPane sourceTextScrollPane = JComponentFactory.createTransparentScrollPane(sourceTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER, 200);
        JScrollPane morseCodeTranslationScrollPane = JComponentFactory.createTransparentScrollPane(morseCodeTranslationArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER, 200);

        setLayout(new GridBagLayout());
        add(sourceTextScrollPane, new GBC(0, 0).setInsets(5, 10, 2, 10).setWeight(1, 0.3).setFill(GBC.BOTH));
        add(morseCodeTranslationScrollPane, new GBC(0, 1).setInsets(2, 10, 5, 10).setWeight(1, 0.7).setFill(GBC.BOTH));
        add(buttonPanel, new GBC(0, 2).setInsets(2, 10, 5, 10).setFill(GBC.HORIZONTAL));
    }

    public void updateTranscription(DocumentEvent event) {
        Document document = event.getDocument();
        SwingUtilities.invokeLater(() -> {
            try {
                String symbol = document.getText(event.getOffset(), event.getLength());
                int symbolsRemoved = 0;

                for (int i = 0; i < symbol.length(); i++) {
                    char c = symbol.charAt(i);

                    if (!MorseDictionary.isSymbolSupported(c) && !DocumentEvent.EventType.REMOVE.equals(event.getType())) {
                        document.remove(event.getOffset() + i - symbolsRemoved, 1);
                        symbolsRemoved++;
                    }
                }
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }

            if (isSourceTextEnabled) {
                String originalText = this.sourceTextArea.getText();
                MorseSymbol[] morseSymbols = TextTranslator.translateToMorse(originalText);

                this.morseCodeTranslationArea.setText(createStringTranscription(morseSymbols));
            }
        });
    }

    public void updateText(DocumentEvent event) {
        if (!isSourceTextEnabled) {
            String transcription = this.morseCodeTranslationArea.getText();
            MorseSymbol[] morseSymbols = convertTranscriptionToMorse(transcription);
            String translatedText = TextTranslator.translateToText(morseSymbols);
            sourceTextArea.setText(translatedText);
        }
    }

    public String createStringTranscription(MorseSymbol[] morseSymbols) {
        StringBuilder builder = new StringBuilder();
        for (MorseSymbol symbol : morseSymbols) {
            boolean[] symbolTranscription = symbol.getTranscription();
            if (symbolTranscription.length == 0) {
                builder.append(SLASH);
            }
            for (boolean flag : symbolTranscription) {
                builder.append(flag ? DASH : DOT);
            }
            builder.append(SPACE);
        }
        return builder.toString();
    }

    public MorseSymbol[] convertTranscriptionToMorse(String transcription) {
        List<MorseSymbol> morseSymbols = new ArrayList<>();
        List<Boolean> morseSymbolElements = new ArrayList<>();
        for (char character : transcription.toCharArray()) {
            if (String.valueOf(character).equals(DASH)) {
                morseSymbolElements.add(true);
            } else if (String.valueOf(character).equals(DOT)) {
                morseSymbolElements.add(false);
            } else if (String.valueOf(character).equals(SPACE)) {
                MorseSymbol symbol = MorseSymbol.of(ArrayUtils.toPrimitive(morseSymbolElements.toArray(new Boolean[0])));
                morseSymbols.add(symbol);
                morseSymbolElements.clear();
            } else if (String.valueOf(character).equals(SLASH)) {
                if (!morseSymbolElements.isEmpty()) {
                    MorseSymbol symbol = MorseSymbol.of(ArrayUtils.toPrimitive(morseSymbolElements.toArray(new Boolean[0])));
                    morseSymbols.add(symbol);
                    morseSymbolElements.clear();
                }
                MorseSymbol spaceSymbol = MorseSymbol.of();
                morseSymbols.add(spaceSymbol);
            }
        }
        if (!morseSymbolElements.isEmpty()) {
            MorseSymbol symbol = MorseSymbol.of(ArrayUtils.toPrimitive(morseSymbolElements.toArray(new Boolean[0])));
            morseSymbols.add(symbol);
        }
        return morseSymbols.toArray(new MorseSymbol[0]);
    }

    public void switchToSourceTextArea(boolean isSourceTextEnabled) {
        if (isSourceTextEnabled == this.isSourceTextEnabled) {
            return;
        }

        this.isSourceTextEnabled = isSourceTextEnabled;
        sourceTextArea.setEditable(isSourceTextEnabled);

        if (isSourceTextEnabled) {
            sourceTextArea.setCaretPosition(sourceTextArea.getText().length());
            sourceTextArea.requestFocus();
        }

        symbolButton.setEnabled(!isSourceTextEnabled);
        spaceButton.setEnabled(!isSourceTextEnabled);
        deleteButton.setEnabled(!isSourceTextEnabled);

        Color enabledColor = AppColorPalette.TEXT_AREA_ENABLED_BACKGROUND;
        Color disabledColor = AppColorPalette.TEXT_AREA_DISABLED_BACKGROUND;

        sourceTextArea.setBackground(isSourceTextEnabled ? enabledColor : disabledColor);
        morseCodeTranslationArea.setBackground(!isSourceTextEnabled ? enabledColor : disabledColor);
    }

    class MorseInputMouseAdapter extends MouseAdapter {

        private long pressedTime;

        private final String shortInputSymbol;
        private final String longInputSymbol;

        public MorseInputMouseAdapter(String shortInputSymbol, String longInputSymbol) {
            this.shortInputSymbol = shortInputSymbol;
            this.longInputSymbol = longInputSymbol;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof JComponent component && component.isEnabled()) {
                pressedTime = System.currentTimeMillis();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getSource() instanceof JComponent component && component.isEnabled()) {
                long delay = System.currentTimeMillis() - pressedTime;
                morseCodeTranslationArea.append(delay >= 500 ? longInputSymbol : shortInputSymbol);
            }
        }
    }

    class SwitchTextAreaMouseAdapter extends MouseAdapter {

        private final boolean isSourceTextEnabled;

        public SwitchTextAreaMouseAdapter(boolean isSourceTextEnabled) {
            this.isSourceTextEnabled = isSourceTextEnabled;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switchToSourceTextArea(isSourceTextEnabled);
        }
    }

}
