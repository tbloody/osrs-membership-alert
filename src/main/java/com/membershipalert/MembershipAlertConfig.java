package com.membershipalert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("membership-alet")
public interface MembershipAlertConfig extends Config {
    @ConfigItem(
            keyName = "membership-days-danger",
            name = "Danger days",
            description = "On how many days to show the danger color"
    )
    default int DangerDays() {
        return 1;
    }

    @ConfigItem(
            keyName = "membership-color-danger",
            name = "Danger color",
            description = "The color to use for the title on danger"
    )
    default Color DangerColor() {
        return Color.RED;
    }

    @ConfigItem(
            keyName = "membership-days-warning",
            name = "Warning days",
            description = "On how many days to show the warning color"
    )
    default int WarningDays() {
        return 3;
    }

    @ConfigItem(
            keyName = "membership-color-warning",
            name = "Warning color",
            description = "The color to use for the title on warning"
    )
    default Color WarningColor() {
        return Color.ORANGE;
    }

    @ConfigItem(
            keyName = "membership-color-success",
            name = "Success color",
            description = "The color to use for the title when we're good on membership days"
    )
    default Color SuccessColor() {
        return Color.GREEN;
    }

    @ConfigItem(
            keyName = "hide-on-success",
            name= "Hide on success",
            description = "Hides the overlay instead of displaying the remaining days in success color."
    )
    default boolean HideOnSuccess() {
        return false;
    }
}
