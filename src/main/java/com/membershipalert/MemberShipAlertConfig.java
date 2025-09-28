package com.membershipalert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface MemberShipAlertConfig extends Config
{
	@ConfigItem(
		keyName = "membership-alert",
		name = "Membership alert",
		description = "This plugin alerts you when your membership is about to expire"
	)
	default boolean Overlay()
	{
		return true;
	}
}
