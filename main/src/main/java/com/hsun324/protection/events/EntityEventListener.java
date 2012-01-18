package com.hsun324.protection.events;

import com.hsun324.protection.ProtectionSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakEvent;

public class EntityEventListener extends EntityListener
{
	private static final EntityEventListener instance = new EntityEventListener();

	public static EntityEventListener getInstance() {
		return instance;
	}

	@Override
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

	@Override
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
}