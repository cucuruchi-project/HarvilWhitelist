package com.cucuruchi.whitelist.listener;

import com.cucuruchi.harvillibrary.extension.ConfigExtension;
import com.cucuruchi.whitelist.message.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

import static com.cucuruchi.harvillibrary.extension.MessageExtension.sendMessage;

public class OnLoginEvent implements Listener {

    private final Message message;
    private List<String> exceptPlayers;
    private boolean whitelist;
    public OnLoginEvent(Message message, List<String> exceptPlayers, boolean whitelist) {
        this.message = message;
        this.exceptPlayers = exceptPlayers;
        this.whitelist = whitelist;
    }

    @EventHandler
    public void onLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player == null)
            return;
        if (whitelist == false) {
            return;
        }
        if (whitelist == true){
            if (exceptPlayers.contains(player.getName())) {
                sendMessage(player, "화이트리스트 예외 플레이어이므로 입장되었습니다.");
                return;
            }
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, message.whitelistMessage());
        }
    }
}
