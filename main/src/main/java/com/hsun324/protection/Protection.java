package com.hsun324.protection;

import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hsun324.protection.commands.LoginCommandHandler;
import com.hsun324.protection.config.ProtectionConfiguration;
import com.hsun324.protection.events.EventListener;

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
		pm.registerEvents(EventListener.getInstance(), this);
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
