package com.cucuruchi.whitelist.message;

import com.cucuruchi.harvillibrary.extension.ConfigExtension;

public class Message {

    private final ConfigExtension messageConfig;
    public Message(ConfigExtension messageConfig) {
        this.messageConfig = messageConfig;
    }

    public String whitelistMessage(){
        return messageConfig.get().getString("whitelist-message");
    }

}
