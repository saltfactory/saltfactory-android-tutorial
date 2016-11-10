package net.hibrain.tutorial.pushserver.models;

/**
 * Created by saltfactory on 10/11/2016.
 */
public class Notification {
    private String title;
    private String text;

    public Notification() {
    }

    public Notification(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
