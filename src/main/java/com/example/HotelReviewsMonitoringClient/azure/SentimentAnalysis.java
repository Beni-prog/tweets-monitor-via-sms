package com.example.HotelReviewsMonitoringClient.azure;

public class SentimentAnalysis {

    private TextDocument document;
    private String sentiment;

    public SentimentAnalysis() {}

    public SentimentAnalysis(TextDocument document, String sentiment) {
        this.document = document;
        this.sentiment = sentiment;
    }

    public TextDocument getDocument() {
        return document;
    }

    public void setDocument(TextDocument document) {
        this.document = document;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "SentimentAnalysis{" +
                "document=" + document +
                ", sentiment='" + sentiment + '\'' +
                '}';
    }
}
