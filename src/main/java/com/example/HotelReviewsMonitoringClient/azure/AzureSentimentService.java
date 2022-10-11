package com.example.HotelReviewsMonitoringClient.azure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;

@Service
public class AzureSentimentService {

    @Value(${"AZURE_API_KEY"})
    private static final String azureAPIKey;
    public static final String AZURE_ENDPOINT = "https://landon-hotel-tweats-monitoring.cognitiveservices.azure.com";
    public static final String AZURE_ENDPOINT_PATH = "/language/:analyze-text?api-version=2022-05-01";
    public static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    public ObjectMapper mapper;

    public SentimentAnalysis requestSentimentAnalysis(String text, String language) throws IOException, InterruptedException {
        TextDocument document = new TextDocument("1", language, text);

        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.setAnalysisInput(new AnalysisInput(Collections.singletonList(document)));
        requestBody.setKind("SentimentAnalysis");
        requestBody.setParameters(new Parameter("latest"));

        //1. Create a client
        HttpClient client = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                //not to wait for API for more than 5 seconds
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        //2. Create a request
        HttpRequest request = HttpRequest.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(API_KEY_HEADER_NAME, azureAPIKey)
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                //mapper serializes the object to a json string
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .timeout(Duration.ofSeconds(5))
                .build();

        //3. Send the request and receive the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("An error occurred while receiving the response from Azure API!");
        }

        return new SentimentAnalysis(document, mapper.readValue(response.body(), JsonNode.class)
                .get("results")
                .get("documents")
                .get(0)
                .get("sentiment")
                .textValue());
    }
}
