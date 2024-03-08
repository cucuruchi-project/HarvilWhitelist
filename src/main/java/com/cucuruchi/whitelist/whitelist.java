package com.cucuruchi.whitelist;

import com.cucuruchi.harvillibrary.extension.ConfigExtension;
import com.cucuruchi.whitelist.command.AdminCommand;
import com.cucuruchi.whitelist.listener.OnLoginEvent;
import com.cucuruchi.whitelist.message.Message;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class whitelist extends JavaPlugin {

    private ConfigExtension config;
    private ConfigExtension messageConfig;


    @Override
    public void onEnable() {
        config = new ConfigExtension(this, "config.yml");
        messageConfig = new ConfigExtension(this, "message.yml");
        Message message = new Message(messageConfig);
        boolean whitelist = config.get().getBoolean("whitelist");
        List<String> exceptPlayers = config.get().getStringList("except-players");

        registerCommand("점검", new AdminCommand(config, whitelist, exceptPlayers, messageConfig));
        getServer().getPluginManager().registerEvents(new OnLoginEvent(message, exceptPlayers, whitelist), this);
    }

    @Override
    public void onDisable() {
    }

    public void registerCommand(String command, CommandExecutor commandExecutor){
        getCommand(command).setExecutor(commandExecutor);
        getCommand(command).setTabCompleter((TabCompleter) commandExecutor);
    }
}
