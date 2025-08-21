package com.util.gui.component;

import com.util.gui.GBC;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OvalFramedButton extends OvalFramedPanel {

    private JLabel buttonTextLabel;
    private ActionListener mouseClickAction;

    public OvalFramedButton(String buttonText) {

        buttonTextLabel = new JLabel(buttonText);

        setLayout(new GridBagLayout());

        add(buttonTextLabel, new GBC(0,0).setInsets(5,15,5,15));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (mouseClickAction != null && isEnabled()) {
                    mouseClickAction.actionPerformed(null);
                }
            }
        });
    }

    public OvalFramedButton() {
        this("");
    }

    public void setButtonText(String buttonText) {
        if (buttonTextLabel == null) {
            buttonTextLabel = new JLabel();
        }

        buttonTextLabel.setText(buttonText);
    }

    public void setActionListener(ActionListener action) {
        mouseClickAction = action;
    }

    @Override
    public void setForeground(Color color) {
        if (buttonTextLabel != null) {
            buttonTextLabel.setForeground(color);
        }
    }

}
