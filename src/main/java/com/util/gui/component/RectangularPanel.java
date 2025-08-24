package com.util.gui.component;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RectangularPanel extends JPanel {

    private boolean isBorderEnabled;

    private int archValue;

    public RectangularPanel() {
        setOpaque(false);
    }

    public void enableBorder(boolean isEnabled) {
        isBorderEnabled = isEnabled;
        repaint();
    }

    public void setArch(int archValue) {
        this.archValue = archValue;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(isBorderEnabled) {
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(5, 5, width - 10, height - 10, archValue, archValue);
        }
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, archValue, archValue);
    }

}
