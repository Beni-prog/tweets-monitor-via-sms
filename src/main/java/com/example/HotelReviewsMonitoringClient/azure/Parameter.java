package com.example.HotelReviewsMonitoringClient.azure;

public class Parameter {
    private String modelVersion;

    public Parameter(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
}
