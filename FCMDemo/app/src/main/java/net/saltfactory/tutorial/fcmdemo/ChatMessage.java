package net.saltfactory.tutorial.fcmdemo;

/**
 * Created by saltfactory on 11/11/2016.
 */

public class ChatMessage {
    private String Content;
    private boolean isMine;

    public ChatMessage(String content, boolean isMine) {
        Content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return Content;
    }

    public boolean isMine() {
        return isMine;
    }
}
