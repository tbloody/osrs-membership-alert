package com.membershipalert;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.VarPlayer;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
        name = "Membership alert"
)
public class MemberShipAlertPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private MemberShipAlertConfig config;

    private int membershipDays = 0;

    @Override
    protected void startUp() throws Exception {
        log.info("Membership alert started!");
        membershipDays = client.getVarpValue(VarPlayer.MEMBERSHIP_DAYS);
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("Membership alert stopped!");
    }

    @Subscribe
    public void onClientTick(ClientTick gameStateChanged) {

        int memDays = client.getVarpValue(VarPlayer.MEMBERSHIP_DAYS);
        if (memDays != membershipDays) {
            membershipDays = memDays;
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Membership days2", "Membership days left: " + membershipDays, null);
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged) {
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
            membershipDays = client.getVarpValue(VarPlayer.MEMBERSHIP_DAYS);
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Membership days", "Membership days left: " + membershipDays, null);
        }
    }

    @Provides
    MemberShipAlertConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(MemberShipAlertConfig.class);
    }
}
