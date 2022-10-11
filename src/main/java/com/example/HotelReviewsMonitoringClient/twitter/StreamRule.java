package com.example.HotelReviewsMonitoringClient.twitter;

public class StreamRule {
    private String value;
    private String tag;

    public StreamRule(String value, String tag) {
        this.value = value;
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
