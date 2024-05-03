package com.funds;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.plugins.xpglobes.XpGlobe;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;
import java.awt.geom.Arc2D;

public class FundsOverlay extends Overlay {
    private final Client client;
    private final FundsConfig fundsConfig;

    @Inject
    public FundsOverlay(Client client, FundsConfig fundsConfig) {
        this.client = client;
        this.fundsConfig = fundsConfig;
    }

    @Override
    public Dimension render(Graphics2D graphics2D) {
        return null;
    }

    private void drawProgressArc(Graphics2D graphics, int x, int y, int w, int h, double radiusStart, double radiusEnd, int strokeWidth, Color color) {
        Stroke stroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.setColor(color);
        graphics.draw(new Arc2D.Double(
                x, y,
                w, h,
                radiusStart, radiusEnd,
                Arc2D.OPEN));
        graphics.setStroke(stroke);
    }

}
