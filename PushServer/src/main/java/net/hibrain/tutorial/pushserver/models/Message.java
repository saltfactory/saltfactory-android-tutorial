package net.hibrain.tutorial.pushserver.models;


/**
 * Created by saltfactory on 10/11/2016.
 */
public class Message {
    private String to;
    private Notification notification;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
