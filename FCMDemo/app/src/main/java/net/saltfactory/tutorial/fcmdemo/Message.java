package net.saltfactory.tutorial.fcmdemo;

/**
 * 채팅방에 사용될 메세지 클래스
 */
public class Message {
    /**
     * 실제 메세지 내용
     */
    String message;
    /**
     * 나의 메세지 인지 상대방의 메세지 인지를 구분 하는 구분자
     */
    boolean isMine;
    /**
     * 메세지 상태
     */
    boolean isStatusMessage;

    public Message(String message, boolean isMine) {
        super();
        this.message = message;
        this.isMine = isMine;
        this.isStatusMessage = false;
    }

    public Message(boolean status, String message) {
        super();
        this.message = message;
        this.isMine = false;
        this.isStatusMessage = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isMine() {
        return isMine;
    }
    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
    public boolean isStatusMessage() {
        return isStatusMessage;
    }
    public void setStatusMessage(boolean isStatusMessage) {
        this.isStatusMessage = isStatusMessage;
    }
}