package com.funds;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Arrays;
import java.util.stream.LongStream;

// I only care about say from our starting session the amount of money that we've gained / lost in that time
@Slf4j
@PluginDescriptor(
	name = "Funds"
)
public class FundsPlugin extends Plugin {
	private long funds;

	@Inject
	private Client client;

	@Inject
	private FundsConfig config;

	@Inject
	private ItemManager itemManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private FundsOverlay overlay;

	@Override
	protected void startUp() throws Exception {
		overlayManager.add(overlay);
		funds = 0L;
	}

	@Override
	protected void shutDown() throws Exception {
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event) {
		int containerId = event.getContainerId();
		if (containerId == InventoryID.INVENTORY.getId() ||
			containerId == InventoryID.EQUIPMENT.getId()) {
			ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
			ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
            assert inventory != null && equipment != null;
            funds += (calculateItemsValues(inventory.getItems())
					+ calculateItemsValues(equipment.getItems()));
			overlay.setProfitValue(funds);
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Have lots of fun today in RuneScape "
					+ client.getLocalPlayer().getName(), null);
		}
	}

	@Provides
	FundsConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(FundsConfig.class);
	}

	private long calculateItemsValues(Item[] items) {
		return Arrays.stream(items).flatMapToLong(item ->
				LongStream.of((long) item.getQuantity() * itemManager.getItemPrice(item.getId()))
		).sum();
	}
}
