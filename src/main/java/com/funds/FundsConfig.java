package com.funds;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface FundsConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting() {
		return "Hello";
	}

	// Reference boolean config element.
	@ConfigItem(
			position = 0,
			keyName = "hideMaxed",
			name = "Hide maxed skills",
			description = "XP Tracker will no longer track level 99 skills"
	)
	default boolean hideMaxed() {
		return false;
	}
}
