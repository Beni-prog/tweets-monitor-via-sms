package com.example.HotelReviewsMonitoringClient.azure;

import java.util.List;

public class AnalysisInput {
    private List<TextDocument> documents;

    public AnalysisInput(List<TextDocument> documents) {
        this.documents = documents;
    }

    public List<TextDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<TextDocument> documents) {
        this.documents = documents;
    }
}
