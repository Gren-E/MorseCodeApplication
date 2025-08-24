package com.util.gui.component;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

public class OvalPanel extends JPanel {

    private boolean isBorderEnabled;

    public OvalPanel() {
        this(new FlowLayout());
    }

    public OvalPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    public void enableBorder(boolean isEnabled) {
        isBorderEnabled = isEnabled;
        repaint();
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

        int arcWidth = Math.min(width, height);

        if(isBorderEnabled) {
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(5, 5, width - 10, height - 10, arcWidth - 10, height - 10);
        }
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, arcWidth, height);
    }

}
