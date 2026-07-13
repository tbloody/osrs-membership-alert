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

    private Boolean DoShow() {
        if (memberShipDays <= config.DangerDays() || config.HideOptions() == MembershipAlertConfig.HideOptions.NEVER) return true;

        if (memberShipDays <= config.WarningDays() && config.HideOptions() != MembershipAlertConfig.HideOptions.WARNING) return true;

        if (memberShipDays > config.WarningDays() && (config.HideOptions() != MembershipAlertConfig.HideOptions.WARNING && config.HideOptions() != MembershipAlertConfig.HideOptions.SUCCESS)) return true;

        return false;
    }

    private Color GetColor() {
        if (memberShipDays > config.WarningDays()) {
            return config.SuccessColor();
        }

        if (memberShipDays > config.DangerDays()) {
            return config.WarningColor();
        }

        return config.DangerColor();
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear();

        if(!DoShow()) return null;

        String overlayTitle = "Membership:";

        panelComponent.getChildren().add(TitleComponent.builder()
                .text(overlayTitle)
                .color(GetColor())
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
