package com.membershipalert;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.VarPlayer;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "Membership alert"
)
public class MembershipAlertPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private MembershipAlertConfig config;

    @Inject
    private MembershipAlertOverlay overlay;

    @Inject
    private OverlayManager overlayManager;

    private int membershipDays = 0;

    @Override
    protected void startUp() throws Exception {
        log.debug("Membership alert started!");
        membershipDays = client.getVarpValue(VarPlayer.MEMBERSHIP_DAYS);
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
        log.debug("Membership alert stopped!");
    }
    @Subscribe
    public void onVarbitChanged(VarbitChanged varBitChanged ) {
        if(varBitChanged.getVarpId()==VarPlayer.MEMBERSHIP_DAYS) {
            int memDays = client.getVarpValue(VarPlayer.MEMBERSHIP_DAYS);
            if (memDays != membershipDays) {
                membershipDays = memDays;
                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Membership days2", "Membership days left: " + membershipDays, null);
                overlay.updateMembershipDays(memDays);
                overlayManager.resetOverlay(overlay);
            }
        }
    }

    @Provides
    MembershipAlertConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(MembershipAlertConfig.class);
    }
}
