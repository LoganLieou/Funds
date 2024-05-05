package com.funds;

import com.google.inject.Inject;
import lombok.Setter;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import java.awt.*;
import java.text.DecimalFormat;

public class FundsOverlay extends Overlay {
    @Setter
    private long profitValue;
    private long startTime;
    private boolean inSession;

    private final FundsConfig fundsConfig;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public FundsOverlay(FundsConfig fundsConfig) {
        setPosition(OverlayPosition.TOP_LEFT);
        profitValue = 0L;
        this.fundsConfig = fundsConfig;
        startTime = 1;
        inSession = true;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        String titleText = "Funds Tracker:";
        long elapsed;

        if (startTime > 0) {
            elapsed = (System.currentTimeMillis() - startTime) / 1000;
        } else {
            elapsed = 0;
        }

        // TITLE
        panelComponent.getChildren().clear();
        panelComponent.getChildren().add(TitleComponent.builder()
                .text(titleText)
                .color(Color.GREEN)
                .build());

        // RESET
        if (!inSession) {
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Reset Plugin")
                    .color(Color.RED)
                    .build());
        }

        // WIDTH
        panelComponent.setPreferredSize(new Dimension(
                graphics.getFontMetrics().stringWidth(titleText) + 40,
                0));

        // PROFITS
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Profit:")
                .right(profitFormat(profitValue))
                .build());

        // TIME
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Time:")
                .right(timeFormat(elapsed))
                .build());

        return panelComponent.render(graphics);
    }

    public static String profitFormat(long profitValue) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(profitValue);
    }

    public static String timeFormat(long elapsedTime) {
        final long sec = elapsedTime % 60;
        final long min = (elapsedTime / 60) % 60;
        final long hr = elapsedTime / 3600;

        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
}
