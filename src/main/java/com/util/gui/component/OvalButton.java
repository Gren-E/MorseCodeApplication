package com.util.gui.component;

import com.util.gui.GBC;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OvalButton extends OvalPanel {

    private JLabel buttonTextLabel;

    private ActionListener mouseClickAction;

    private Color backgroundColor;
    private Color highlightColor;

    public OvalButton() {
        this("");
    }

    public OvalButton(String buttonText) {
        buttonTextLabel = new JLabel(buttonText);
        backgroundColor = getBackground();

        addMouseListener(new OvalButtonMouseAdapter());

        setLayout(new GridBagLayout());
        add(buttonTextLabel, new GBC(0,0).setInsets(5,15,5,15));
    }

    public void setButtonText(String buttonText) {
        buttonTextLabel.setText(buttonText);
    }

    public void setActionListener(ActionListener action) {
        mouseClickAction = action;
    }

    public void setBackgroundColor(Color color) {
        super.setBackground(color);
        backgroundColor = color;
    }

    public void setHighlightColor(Color color) {
        highlightColor = color;
    }

    @Override
    public void setForeground(Color color) {
        if (buttonTextLabel != null) {
            buttonTextLabel.setForeground(color);
        }
    }

    class OvalButtonMouseAdapter extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            if(isEnabled() && highlightColor != null) {
                setBackground(highlightColor);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (backgroundColor != null) {
                setBackground(backgroundColor);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (mouseClickAction != null && isEnabled()) {
                mouseClickAction.actionPerformed(null);
            }
        }
    }

}
