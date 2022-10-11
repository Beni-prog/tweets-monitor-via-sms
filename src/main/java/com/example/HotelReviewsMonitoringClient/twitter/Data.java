package com.example.HotelReviewsMonitoringClient.twitter;

public class Data {

    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
