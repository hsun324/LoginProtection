package com.hsun324.protection.events;

import com.hsun324.protection.Protection;
import com.hsun324.protection.ProtectionSystem;
import com.hsun324.protection.config.ProtectionConfiguration;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class PlayerEventListener extends PlayerListener
{
	private static final PlayerEventListener instance = new PlayerEventListener();

	public static PlayerEventListener getInstance() {
		return instance;
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			event.getPlayer().teleport(event.getFrom());
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerChat(PlayerChatEvent event)
	{
		ProtectionSystem.updatePlayerUsername(event.getPlayer());
	}

	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		PluginCommand command = Protection.getInstance().getServer().getPluginCommand(event.getMessage().substring(1).split(" ")[0]);
		if ((command != null) && 
			(command.getPlugin() == Protection.getInstance()))
			return;
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer(), false);
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		ProtectionSystem.createStatusIfNone(event.getPlayer());
		ProtectionSystem.setPlayerLoggedIn(event.getPlayer().getName(), ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()));
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		String address = event.getPlayer().getAddress().getAddress().getHostAddress();
		if (ProtectionSystem.isPlayerBanned(address))
			event.getPlayer().kickPlayer("\u00A7e[LoginProtection]\u00A7f Your IP is still banned. Time Left: \u00A76" + ProtectionSystem.getPlayerBanTimeLeft(address) + " seconds");
		if (!ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()))
			event.getPlayer().sendMessage("\u00A7e[LoginProtection] Remeber to login before you do anything. \u00A77See /protection for more info.");
	}

	@Override
	public void onPlayerKick(PlayerKickEvent event)
	{
		ProtectionSystem.setPlayerLoggedIn(event.getPlayer().getName(), ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()));
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		ProtectionSystem.setPlayerLoggedIn(event.getPlayer().getName(), ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()));
	}
}