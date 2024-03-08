package com.cucuruchi.whitelist.message;

import com.cucuruchi.harvillibrary.extension.ColorExtension;
import com.cucuruchi.harvillibrary.extension.MessageExtension;
import com.cucuruchi.harvillibrary.extension.StringExtension;
import org.bukkit.entity.Player;

public class HelpMessage {
    static final String prefix = ColorExtension.process(StringExtension.transChatColor("&f[<GRADIENT:B58AD8> WHITELIST </GRADIENT:4D3278>&f]"));


    public static void sendHelpMessage(Player player){
        MessageExtension.helpMessage(player,
                prefix, "/점검",
                "시작",
                "종료",
                "인원추가",
                "인원제거",
                "인원목록",
                "도움말");
    }
}
