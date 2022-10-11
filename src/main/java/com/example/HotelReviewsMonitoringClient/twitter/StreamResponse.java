package com.example.HotelReviewsMonitoringClient.twitter;

public class StreamResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StreamResponse{" +
                "data=" + data +
                '}';
    }
}
