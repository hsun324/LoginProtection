package com.hsun324.protection.events;

import com.hsun324.protection.Protection;
import com.hsun324.protection.ProtectionSystem;
import com.hsun324.protection.config.ProtectionConfiguration;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class EventListener implements Listener
{
	private static final EventListener instance = new EventListener();

	public static EventListener getInstance() {
		return instance;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockDamage(BlockDamageEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPaintingBreak(PaintingBreakEvent event)
	{
		if ((event instanceof PaintingBreakByEntityEvent))
		{
			PaintingBreakByEntityEvent entityEvent = (PaintingBreakByEntityEvent)event;
			if ((entityEvent.getRemover() instanceof Player))
			{
				Player remover = (Player)entityEvent.getRemover();
				if (!ProtectionSystem.isPlayerLoggedIn(remover.getName()))
				{
					ProtectionSystem.warn(remover);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if ((event.getEntity() instanceof Player))
		{
			EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent)event;
			if ((entityEvent.getDamager() instanceof Player))
			{
				Player damager = (Player)entityEvent.getDamager();
				if (!ProtectionSystem.isPlayerLoggedIn(damager.getName()))
				{
					ProtectionSystem.warn(damager);
					event.setCancelled(true);
				}
			}
		}
		if ((event instanceof EntityDamageByEntityEvent))
		{
			EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent)event;
			if ((entityEvent.getDamager() instanceof Player))
			{
				Player damager = (Player)entityEvent.getDamager();
				if (!ProtectionSystem.isPlayerLoggedIn(damager.getName()))
				{
					ProtectionSystem.warn(damager);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (!ProtectionSystem.isPlayerLoggedIn(event.getPlayer().getName()))
		{
			event.getPlayer().teleport(event.getFrom());
			ProtectionSystem.warn(event.getPlayer());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event)
	{
		ProtectionSystem.updatePlayerUsername(event.getPlayer());
	}

	@EventHandler
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

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		ProtectionSystem.createStatusIfNone(event.getPlayer());
		ProtectionSystem.setPlayerLoggedIn(event.getPlayer().getName(), ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		String address = event.getPlayer().getAddress().getAddress().getHostAddress();
		if (ProtectionSystem.isPlayerBanned(address))
			event.getPlayer().kickPlayer("\u00A7e[LoginProtection]\u00A7f Your IP is still banned. Time Left: \u00A76" + ProtectionSystem.getPlayerBanTimeLeft(address) + " seconds");
		if (!ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()))
			event.getPlayer().sendMessage("\u00A7e[LoginProtection] Remeber to login before you do anything. \u00A77See /protection for more info.");
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event)
	{
		ProtectionSystem.setPlayerLoggedIn(event.getPlayer().getName(), ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		ProtectionSystem.setPlayerLoggedIn(event.getPlayer().getName(), ProtectionConfiguration.getDefaultLoginStatus(event.getPlayer()));
	}
}