package com.hsun324.protection;

import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hsun324.protection.commands.LoginCommandHandler;
import com.hsun324.protection.config.ProtectionConfiguration;
import com.hsun324.protection.events.BlockEventListener;
import com.hsun324.protection.events.EntityEventListener;
import com.hsun324.protection.events.PlayerEventListener;

public class Protection extends JavaPlugin
{
	private static Protection instance;
	public static Protection getInstance()
	{
		return instance;
	}
	
	private Logger logger = Logger.getLogger("Minecraft.Protection");
	
	public Logger getLogger()
	{
		return logger;
	}
	
	@Override
	public void onDisable()
	{
		ProtectionSystem.saveToFile();
		logger.info("[LoginProtection] Disabled");
	}

	@Override
	public void onEnable()
	{
		instance = this;
		
		ProtectionSystem.readFromFile();
		ProtectionConfiguration.readFromFile();
		
		if(this.isEnabled())
		{
			bindEvents(getServer().getPluginManager());
			bindCommands();
			
			for(Player player : getServer().getOnlinePlayers())
			{
				ProtectionSystem.createStatusIfNone(player);
				ProtectionSystem.setPlayerLoggedIn(player.getName(), ProtectionConfiguration.getDefaultLoginStatus(player));
				if(!ProtectionConfiguration.getDefaultLoginStatus(player))
					player.sendMessage("\u00A7e[LoginProtection] You may need to login again. I am sincerely sorry for the inconvenience.");
			}
			
			logger.info("[LoginProtection] Enabled");
		}
	}
	
	private void bindEvents(PluginManager pm)
	{
		BlockEventListener block = BlockEventListener.getInstance();
		EntityEventListener entity = EntityEventListener.getInstance();
		PlayerEventListener player = PlayerEventListener.getInstance();
		
		pm.registerEvent(Type.BLOCK_BREAK, block, Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_DAMAGE, block, Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_PLACE, block, Priority.Highest, this);
		
		pm.registerEvent(Type.ENTITY_DAMAGE, entity, Priority.Highest, this);
		pm.registerEvent(Type.PAINTING_BREAK, entity, Priority.Highest, this);

		pm.registerEvent(Type.PLAYER_CHAT, player, Priority.Lowest, this);
		
		pm.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_DROP_ITEM, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_INTERACT, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_INTERACT_ENTITY, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_ITEM_HELD, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_JOIN, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_KICK, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_LOGIN, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_MOVE, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_PICKUP_ITEM, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_QUIT, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_TOGGLE_SNEAK, player, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_TOGGLE_SPRINT, player, Priority.Highest, this);
	}
	
	private void bindCommands()
	{
		PluginCommand protectionCommand = this.getCommand("loginprotection");
		if(protectionCommand == null)
		{
			logger.warning("[LoginProtection] Disabled: Command Error");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		protectionCommand.setExecutor(LoginCommandHandler.getInstance());
	}
}
