package com.example.HotelReviewsMonitoringClient.azure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

@SpringBootTest
public class AzureNamedEntitiesTest {
    @Value("${AZURE_API_KEY}")
    private static final String azureAPIKey;
    public static final String AZURE_ENDPOINT = "https://landon-hotel-tweats-monitoring.cognitiveservices.azure.com";
    public static final String AZURE_ENDPOINT_PATH = "/language/:analyze-text?api-version=2022-05-01";
    public static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String EXAMPLE_JSON = "{\n" +
            "  \"kind\": \"SentimentAnalysis\",\n" +
            "  \"parameters\": {\n" +
            "    \"modelVersion\": \"latest\"\n" +
            "  },\n" +
            "  \"analysisInput\": {\n" +
            "    \"documents\": [\n" +
            "      {\n" +
            "        \"id\": \"1\",\n" +
            "        \"language\": \"en\",\n" +
            "        \"text\": \"Great atmosphere. Close to plenty of restaurants, hotels, and transit! Staff are friendly and helpful.\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";
    private final String textForAnalysis = "Trump’s policy record included major changes at home and abroad. He achieved a string of long-sought conservative victories domestically, including the biggest corporate tax cuts on record, the elimination of scores of environmental regulations and a reshaping of the federal judiciary. In the international arena, he imposed tough new immigration restrictions, withdrew from several multilateral agreements, forged closer ties with Israel and launched a tit-for-tat trade dispute with China as part of a wider effort to address what he saw as glaring imbalances in America’s economic relationship with other countries. ";

    @Autowired
    public ObjectMapper mapper;

    @Test
    public void getEntities() throws IOException, InterruptedException {
        TextDocument document = new TextDocument("1", "en", textForAnalysis);

        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.setAnalysisInput(new AnalysisInput(Collections.singletonList(document)));
        requestBody.setKind("SentimentAnalysis");
        requestBody.setParameters(new Parameter("latest"));

        //1. Create a client
        HttpClient client = HttpClient.newHttpClient();
        //2. Create a request
        HttpRequest request = HttpRequest.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(API_KEY_HEADER_NAME, azureAPIKey)
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                //mapper serializes the object to a json string
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .build();
        System.out.println(mapper.writeValueAsString(requestBody));
        //3'. Send the request and receive response - SYNC
        //this request sent to azure cognitive services is synchronously, i.e.,
        //API blocked the execution of this code: once we sent the request, the code didn't continue its execution,
        //it had to wait for API response to come back
        //HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //3". Send the request and receive the response - ASYNC
        //this is an asynchronously request, i.e.,
        //the code continue its execution, without waiting for the API response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.body())
                .thenAccept(body -> {
                    JsonNode node;
                    try {
                        node = mapper.readValue(body, JsonNode.class);
                        String value = node.get("results")
                                .get("documents")
                                .get(0)
                                .get("sentiment")
                                .textValue();
                        System.out.println("This is my value: " + value);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("This will be called first, due to async request.");
        Thread.sleep(5000);
    }
}
