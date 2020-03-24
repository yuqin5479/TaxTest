package com.ruihua.geshuibao.Bean;

public class ChatMessage {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;
    private int type;
    private String megHeadUrl;

    public String getMegHeadUrl() {
        return megHeadUrl;
    }

    public void setMegHeadUrl(String megHeadUrl) {
        this.megHeadUrl = megHeadUrl;
    }

    public ChatMessage(String content, int type, String megHeadUrl) {
        this.content = content;
        this.type = type;
        this.megHeadUrl = megHeadUrl;
    }



    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
