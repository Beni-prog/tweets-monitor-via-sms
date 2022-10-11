package com.example.HotelReviewsMonitoringClient.azure;

import java.util.ArrayList;

public class TextAnalyticsRequest {
    private String kind;
    private Parameter parameters;
    private AnalysisInput analysisInput = new AnalysisInput(new ArrayList<>());

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Parameter getParameters() {
        return parameters;
    }

    public void setParameters(Parameter parameters) {
        this.parameters = parameters;
    }

    public AnalysisInput getAnalysisInput() {
        return analysisInput;
    }

    public void setAnalysisInput(AnalysisInput analysisInput) {
        this.analysisInput = analysisInput;
    }
}
