package com.cucuruchi.whitelist.command;

import com.cucuruchi.harvillibrary.extension.ConfigExtension;
import com.cucuruchi.whitelist.message.HelpMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static com.cucuruchi.harvillibrary.extension.MessageExtension.sendMessage;

public class AdminCommand implements CommandExecutor, TabCompleter {

    private final ConfigExtension config;
    private List<String> exceptPlayers;
    private boolean whitelist;
    private final ConfigExtension messageConfig;
    public AdminCommand(ConfigExtension config, boolean whitelist, List<String> exceptPlayers, ConfigExtension messageConfig) {
        this.config = config;
        this.whitelist = whitelist;
        this.exceptPlayers = exceptPlayers;
        this.messageConfig = messageConfig;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) { return false; }

        if (!player.isOp() || !player.hasPermission("harvilwhitelist.admin")) { return false; }

        if (args.length == 0){
            HelpMessage.sendHelpMessage(player);
            return true;
        }

        switch (args[0]) {
            case "시작":
                if (whitelist == true) { break; }

                whitelist = true;
                config.set("whitelist", true);
                sendMessage(player, "&a점검이 활성화 되었습니다.");
                break;
            case "종료":
                if (whitelist == false) { break; }

                whitelist = false;
                config.set("whitelist", false);
                sendMessage(player, "&c점검이 비활성화 되었습니다.");
                break;
            case "인원추가":
                if (args.length < 2){
                    sendMessage(player, "&c올바른 명령어를 입력해주세요.");
                    break;
                }
                exceptPlayers.add(args[1]);
                config.set("except-players", exceptPlayers.toArray());
                sendMessage(player, "&c" + args[1] + "님을 예외 플레이어로 등록하였습니다.");
                break;
            case "인원삭제":
                if (args.length < 2){
                    sendMessage(player, "&c올바른 명령어를 입력해주세요.");
                    break;
                }
                exceptPlayers.remove(args[0]);
                config.set("except-players", exceptPlayers);
                sendMessage(player, "&c" + args[1] + "님을 예외 플레이어에서 제거하였습니다.");
                break;
            case "인원목록":
                sendMessage(player, "&a예외 플레이어 목록");
                exceptPlayers.forEach(str -> sendMessage(player, " &6 - " + str + "\n"));
                break;
            case "리로드":
                if (args.length < 2){
                    config.reload();
                    messageConfig.reload();
                    sendMessage(player, "모든 설정 파일을 리로드했습니다.");
                    break;
                }
                switch (args[1]){
                    case "config":
                        config.reload();
                        sendMessage(player, "config.yml 파일을 리로드했습니다.");
                        break;
                    case "message":
                        messageConfig.reload();
                        sendMessage(player, "messageConfig.yml 파일을 리로드했습니다.");
                        break;
                    default:
                        sendMessage(player, "&c올바른 명령어를 입력해주세요.");
                        break;
                }

            case "도움말":
                HelpMessage.sendHelpMessage(player);
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return switch (args[0]) {
            case "" -> List.of("시작", "종료", "인원추가", "인원삭제", "인원목록", "리로드", "도움말");
            case "인원추가" -> List.of(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
            case "인원삭제" -> exceptPlayers;
            case "리로드" -> List.of("config", "message");
            default -> Collections.emptyList();
        };
    }
}
