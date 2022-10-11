package com.example.HotelReviewsMonitoringClient.twilio;

import com.example.HotelReviewsMonitoringClient.azure.SentimentAnalysis;
import com.example.HotelReviewsMonitoringClient.azure.TextAnalyticsRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AzureSentimentClient {

    @Headers({"Ocp-Apim-Subscription-Key: {apiKey}","Content-Type: application/json"})
    @RequestLine("POST /language/:analyze-text?api-version=2022-05-01")
    SentimentAnalysis analyze(@Param("apiKey") String apiKey, TextAnalyticsRequest request);
}
