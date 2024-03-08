package com.cucuruchi.whitelist;

import com.cucuruchi.harvillibrary.extension.ConfigExtension;
import com.cucuruchi.whitelist.command.AdminCommand;
import com.cucuruchi.whitelist.listener.OnLoginEvent;
import com.cucuruchi.whitelist.message.HelpMessage;
import com.cucuruchi.whitelist.message.Message;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class whitelist extends JavaPlugin {

    boolean whitelist;
    private List<String> exceptPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        ConfigExtension config = new ConfigExtension(this, "config.yml");
        ConfigExtension messageConfig = new ConfigExtension(this, "message.yml");
        Message message = new Message(messageConfig);
        whitelist = config.get().getBoolean("whitelist");
        exceptPlayers = config.get().getStringList("exceptPlayers");

        registerCommand("점검", new AdminCommand(config, whitelist, exceptPlayers, messageConfig));
        getServer().getPluginManager().registerEvents(new OnLoginEvent(message, exceptPlayers, whitelist), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommand(String command, CommandExecutor commandExecutor){
        getCommand(command).setExecutor(commandExecutor);
        getCommand(command).setTabCompleter((TabCompleter) commandExecutor);
    }
}
