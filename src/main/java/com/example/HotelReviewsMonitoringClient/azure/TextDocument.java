package com.example.HotelReviewsMonitoringClient.azure;

public class TextDocument {
    private String id;
    private String language;
    private String text;

    public TextDocument(String id, String language, String text) {
        this.id = id;
        this.language = language;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextDocument{" +
                "id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
