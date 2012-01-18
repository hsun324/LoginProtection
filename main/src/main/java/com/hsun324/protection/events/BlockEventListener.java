package com.hsun324.protection.events;

import com.hsun324.protection.ProtectionSystem;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventListener extends BlockListener
{
	private static final BlockEventListener instance = new BlockEventListener();

	public static BlockEventListener getInstance() {
		return instance;
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onBlockDamage(BlockDamageEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}
}