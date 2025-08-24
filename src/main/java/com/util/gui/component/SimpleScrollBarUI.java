package com.util.gui.component;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

public class SimpleScrollBarUI extends BasicScrollBarUI  {

    @Override
    public void paintTrack(Graphics g, JComponent component, Rectangle trackBounds) {
        g.setColor(component.getBackground());
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    public void paintThumb(Graphics g, JComponent component, Rectangle thumbBounds) {
        g.setColor(component.getForeground());
        g.fillRect(thumbBounds.x + 3, thumbBounds.y + 3, thumbBounds.width - 12, thumbBounds.height - 12);
    }

    @Override
    protected void layoutVScrollbar(JScrollBar scrollBar) {
        Dimension sbSize = scrollBar.getSize();
        Insets sbInsets = scrollBar.getInsets();

        int itemWidth = sbSize.width - (sbInsets.left + sbInsets.right);
        int itemX = sbInsets.left;

        int decrButtonHeight = 0;
        int decrButtonY = sbInsets.top;

        int incrButtonHeight = 0;
        int incrButtonY = sbSize.height - (sbInsets.bottom + incrButtonHeight);

        int sbInsetsHeight = sbInsets.top + sbInsets.bottom;
        int sbButtonsHeight = 0;
        int gaps = decrGap + incrGap;
        float trackHeight = sbSize.height - (sbInsetsHeight + sbButtonsHeight) - gaps;

        float min = scrollBar.getMinimum();
        float extent = scrollBar.getVisibleAmount();
        float range = scrollBar.getMaximum() - min;
        float value = scrollBar.getValue();

        int thumbHeight = range <= 0 ? getMaximumThumbSize().height : (int)(trackHeight * (extent / range));
        thumbHeight = Math.max(thumbHeight, getMinimumThumbSize().height);
        thumbHeight = Math.min(thumbHeight, getMaximumThumbSize().height);

        int thumbY = incrButtonY - incrGap - thumbHeight;
        if (value < (scrollBar.getMaximum() - scrollBar.getVisibleAmount())) {
            float thumbRange = trackHeight - thumbHeight;
            thumbY = (int)(0.5f + (thumbRange * ((value - min) / (range - extent))));
            thumbY +=  decrButtonY + decrButtonHeight + decrGap;
        }

        int sbAvailButtonHeight = (sbSize.height - sbInsetsHeight);
        if (sbAvailButtonHeight < sbButtonsHeight) {
            incrButtonHeight = decrButtonHeight = sbAvailButtonHeight / 2;
            incrButtonY = sbSize.height - (sbInsets.bottom + incrButtonHeight);
        }
        decrButton.setBounds(itemX, decrButtonY, itemWidth, decrButtonHeight);
        incrButton.setBounds(itemX, incrButtonY, itemWidth, incrButtonHeight);

        int itrackY = decrButtonY + decrButtonHeight + decrGap;
        int itrackHeight = incrButtonY - incrGap - itrackY;
        trackRect.setBounds(itemX, itrackY, itemWidth, itrackHeight);

        if(thumbHeight >= (int)trackHeight) {
            if (UIManager.getBoolean("ScrollBar.alwaysShowThumb")) {
                setThumbBounds(itemX, itrackY, itemWidth, itrackHeight);
            } else {
                setThumbBounds(0, 0, 0, 0);
            }
        } else {
            if ((thumbY + thumbHeight) > incrButtonY - incrGap) {
                thumbY = incrButtonY - incrGap - thumbHeight;
            }
            if (thumbY  < (decrButtonY + decrButtonHeight + decrGap)) {
                thumbY = decrButtonY + decrButtonHeight + decrGap + 1;
            }
            setThumbBounds(itemX, thumbY, itemWidth, thumbHeight);
        }
    }

}
