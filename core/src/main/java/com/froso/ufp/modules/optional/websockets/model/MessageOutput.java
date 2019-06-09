package com.froso.ufp.modules.optional.websockets.model;

public class MessageOutput {

    private String from;
    private String text;
    private String time;

    public MessageOutput(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public MessageOutput() {
    }

    public MessageOutput(String from, String text, String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    // getters and setters
}