package com.example.HotelReviewsMonitoringClient.twilio;

import com.example.HotelReviewsMonitoringClient.azure.*;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class AzureSentimentClientTest {
    private static final String apiKey = "296cbd6e145d4e828f8fdb29b4ac17ba";
    public static final String AZURE_ENDPOINT = "https://landon-hotel-tweats-monitoring.cognitiveservices.azure.com";

    @Test
    public void testFeignPositiveSentiment() {
        TextDocument document = new TextDocument("1", "Landon Hotel is the best!", "en");
        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.setAnalysisInput(new AnalysisInput(Collections.singletonList(document)));
        requestBody.setKind("SentimentAnalysis");
        requestBody.setParameters(new Parameter("latest"));

        AzureSentimentClient client = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(AzureSentimentClient.class, AZURE_ENDPOINT);

        SentimentAnalysis analysis = client.analyze(apiKey, requestBody);

        assertNotNull(analysis);
    }
}
