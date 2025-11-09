package com.membershipalert;

import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import java.awt.*;

class MembershipAlertOverlay extends Overlay {
    private final Client client;
    private final MembershipAlertConfig config;
    private final PanelComponent panelComponent = new PanelComponent();

    private int memberShipDays = 0;

    @Inject
    private MembershipAlertOverlay(Client client, MembershipAlertConfig config) {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.client = client;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear();
        String overlayTitle = "Membership:";
        Color color = config.DangerColor();

        if (memberShipDays > config.DangerDays()) {
            color = config.WarningColor();
        }

        if (memberShipDays > config.WarningDays()) {
            if (config.HideOnSuccess() ) {return null;}
            color = config.SuccessColor();
        }

        panelComponent.getChildren().add(TitleComponent.builder()
                .text(overlayTitle)
                .color(color)
                .build());

        panelComponent.setPreferredSize(new Dimension(
                graphics.getFontMetrics().stringWidth(overlayTitle) + 30,
                0));

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Days left:")
                .right(Integer.toString(memberShipDays))
                .build());

        return panelComponent.render(graphics);
    }

    public void updateMembershipDays(int memberShipDays) {
        this.memberShipDays = memberShipDays;
    }
}
